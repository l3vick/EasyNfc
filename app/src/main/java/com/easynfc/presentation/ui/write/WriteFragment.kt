package com.easynfc.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.easynfc.R
import com.easynfc.data.model.Text
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.custom.AutoFitEditText
import com.easynfc.presentation.viewmodel.TagsViewModel
import instanceOf
import kotlinx.android.synthetic.main.fragment_read.view.*
import kotlinx.android.synthetic.main.fragment_write.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.view.MotionEvent










class WriteFragment : BaseFragment() {

    private lateinit var v: View
    private lateinit var textContent: AutoFitEditText

    private lateinit var tagsViewModel: TagsViewModel

    companion object {
        fun newInstance() = instanceOf<WriteFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_write, container, false)
        setupUI()
        v.btnSave.onClick {
            tagsViewModel.insert(Text(textContent.text.toString(), 12312312312312L))
        }
        return v
    }

    private fun setupUI(){
        textContent = v.etContent
        tagsViewModel = ViewModelProviders.of(this).get(TagsViewModel::class.java)
    }

    fun resetHint() {
        textContent.hint = getString(R.string.et_writer_hint)
        textContent.isCursorVisible = false
    }
}
