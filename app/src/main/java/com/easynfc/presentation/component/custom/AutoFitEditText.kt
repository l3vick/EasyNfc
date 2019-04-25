package com.easynfc.presentation.component.custom

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.easynfc.App
import com.easynfc.R

/**
 * Created by varsovski on 29-Oct-15.
 * Updated by Igor Wojda
 *
 * Class allows for input text auto sizing
 */

@Suppress("MagicNumber")
class AutoFitEditText constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private val _availableSpaceRect = RectF()
    private val _sizeTester: SizeTester
    private var _maxTextSize: Float = 1.0F
    private var _spacingMult = 1.0f
    private var _spacingAdd = 0.0f
    private var _minTextSize: Float = 1.0F
    private var _widthLimit: Int = 0
    private var _maxLines: Int = 0
    private var _initialized = false
    private var _textPaint: TextPaint? = null

    companion object {
        private const val NO_LINE_LIMIT = -1
    }

    init {
        // using the minimal recommended font size
        _minTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            20f, resources.displayMetrics
        )
        _maxTextSize = textSize
        if (_maxLines == 0)
        // no value was assigned during construction
            _maxLines = NO_LINE_LIMIT
        // prepare size tester:
        _sizeTester = object : SizeTester {
            val textRect = RectF()

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onTestSize(
                suggestedSize: Int,
                availableSpace: RectF
            ): Int {
                _textPaint?.textSize = suggestedSize.toFloat()
                val text = if (text.toString().isNotBlank()) {
                    text.toString()
                } else {
                    hint.toString()
                }

                if (maxLines == 1) {
                    _textPaint?.let {
                        textRect.bottom = it.fontSpacing
                        textRect.right = it.measureText(text)
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val layout = StaticLayout(
                        text, _textPaint,
                        _widthLimit, Layout.Alignment.ALIGN_NORMAL, _spacingMult,
                        _spacingAdd, true
                    )
                    if (maxLines != NO_LINE_LIMIT && layout.lineCount > maxLines)
                        return 1
                    textRect.bottom = layout.height.toFloat()
                    var maxWidth = -1
                    for (i in 0 until layout.lineCount)
                        if (maxWidth < layout.getLineWidth(i))
                            maxWidth = layout.getLineWidth(i).toInt()
                    textRect.right = maxWidth.toFloat()
                }
                textRect.offsetTo(0f, 0f)
                return if (availableSpace.contains(textRect)) -1 else 1
                // else, too big
            }
        }
        _initialized = true
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.hint = App.resources.getString(R.string.et_writer_hint_wr)
        this.isCursorVisible = true
        return super.onTouchEvent(event)
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.action == 1) {
            super.onKeyPreIme(keyCode, event)
            if (this.text!!.isEmpty()) this.hint = App.resources.getString(R.string.et_writer_hint) ; this.isCursorVisible = false
            return false
        }
        return super.onKeyPreIme(keyCode, event)
    }


    override fun setTypeface(tf: Typeface?) {
        if (_textPaint == null) {
            _textPaint = TextPaint(paint)
        }

        _textPaint?.typeface = tf
        super.setTypeface(tf)
    }

    override fun setTextSize(size: Float) {
        _maxTextSize = size
        adjustTextSize()
    }

    override fun getMaxLines(): Int {
        return _maxLines
    }

    override fun setMaxLines(maxlines: Int) {
        super.setMaxLines(maxlines)
        _maxLines = maxlines
        adjustTextSize()
    }

    override fun setSingleLine() {
        super.setSingleLine()
        _maxLines = 1
        adjustTextSize()
    }

    override fun setSingleLine(singleLine: Boolean) {
        super.setSingleLine(singleLine)
        _maxLines = if (singleLine) 1 else NO_LINE_LIMIT
        adjustTextSize()
    }

    override fun setLines(lines: Int) {
        super.setLines(lines)
        _maxLines = lines
        adjustTextSize()
    }

    override fun setTextSize(unit: Int, size: Float) {
        val resources: Resources = if (context == null) Resources.getSystem() else context.resources

        _maxTextSize = TypedValue.applyDimension(
            unit, size,
            resources.displayMetrics
        )

        adjustTextSize()
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        _spacingMult = mult
        _spacingAdd = add
    }

    /**
     * Set the lower text size limit and invalidate the view
     *
     * @param
     */
    fun setMinTextSize(minTextSize: Float?) {
        this._minTextSize = minTextSize ?: 0.0F
        adjustTextSize()
    }

    private fun adjustTextSize() {
        if (!_initialized)
            return

        val startSize = Math.round(_minTextSize)
        val heightLimit = (measuredHeight - compoundPaddingBottom - compoundPaddingTop)

        _widthLimit = (measuredWidth - compoundPaddingLeft - compoundPaddingRight)

        if (_widthLimit <= 0)
            return

        _availableSpaceRect.right = _widthLimit.toFloat()
        _availableSpaceRect.bottom = heightLimit.toFloat()

        super.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            binarySearch(
                startSize, _maxTextSize.toInt(),
                _sizeTester, _availableSpaceRect
            ).toFloat()
        )
    }

    private fun binarySearch(start: Int, end: Int, sizeTester: SizeTester, availableSpace: RectF): Int {
        var lastBest = start
        var lo = start
        var hi = end - 1
        var mid: Int
        while (lo <= hi) {
            mid = (lo + hi).ushr(1)

            val midValCmp = sizeTester.onTestSize(mid, availableSpace)

            when {
                midValCmp < 0 -> {
                    lastBest = lo
                    lo = mid + 1
                }
                midValCmp > 0 -> {
                    hi = mid - 1
                    lastBest = hi
                }
                else -> return mid
            }
        }
        // make sure to return last best
        // this is what should always be returned
        return lastBest
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, after: Int) {
        super.onTextChanged(text, start, before, after)
        adjustTextSize()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        if (width != oldwidth || height != oldheight) {
            adjustTextSize()
        }
    }

    private interface SizeTester {
        /**
         * AutoFitEditText
         *
         * @param suggestedSize Size of text to be tested
         * @param availableSpace available space in which text must fit
         * @return an integer < 0 if after applying `suggestedSize` to
         * text, it takes less space than `availableSpace`, > 0
         * otherwise
         */
        fun onTestSize(suggestedSize: Int, availableSpace: RectF): Int
    }
}
