package com.easynfc.presentation.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.read.ReadActivity
import instanceOf
import kotlinx.android.synthetic.main.fragment_loading.view.*
import com.easynfc.presentation.component.animation.LTRViewAnimation
import com.easynfc.presentation.component.animation.RTLViewAnimation
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat
import org.jetbrains.anko.image
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler


class LoadingFragment : BaseFragment() {

    lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<LoadingFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_loading, container, false)
        setupUI()
        return v
    }


    override fun onPause() {
        super.onPause()
        //(activity as ReadActivity).nfcManager?.disableForegroundDispatch()
    }


    override fun onResume() {
        super.onResume()
       // (activity as ReadActivity).nfcManager?.enableForegroundDispatch()
    }

    fun setupUI(){
        startAnimations()
    }

    fun startAnimations(){
        v.ivTag.startAnimation(RTLViewAnimation(initialAnimationListener))
        v.ivPhone.startAnimation(LTRViewAnimation())
    }

    val initialAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            val rotation = AnimationUtils.loadAnimation(context, R.anim.rotation)
            rotation.setAnimationListener(rotationAnimationListener)
            v.ivTag.startAnimation(rotation)
            val scaleDownX = ObjectAnimator.ofFloat(v.ivPhone, "scaleX", 0.9f)
            val scaleDownY = ObjectAnimator.ofFloat(v.ivPhone, "scaleY", 0.9f)
            scaleDownX.duration = 500
            scaleDownY.duration = 500
            val scaleDown = AnimatorSet()
            scaleDown.play(scaleDownX).with(scaleDownY)
            scaleDown.start()
        }
    }


    val rotationAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            val scaleDownX = ObjectAnimator.ofFloat(v.ivPhone, "scaleX", 1.0f)
            val scaleDownY = ObjectAnimator.ofFloat(v.ivPhone, "scaleY", 1.0f)
            scaleDownX.duration = 500
            scaleDownY.duration = 500
            val scaleDown = AnimatorSet()
            scaleDown.play(scaleDownX).with(scaleDownY)
            scaleDown.start()

            v.ivTag.clearAnimation()
            val fadeOut = AlphaAnimation(1f, 0f)
            fadeOut.duration = 500
            fadeOut.setAnimationListener(fadeOutAnimationListener)
            v.ivTag.startAnimation(fadeOut)
        }
    }


    val fadeOutAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            if (context != null) {
                v.ivTag.image = ContextCompat.getDrawable(context!!, R.drawable.ic_code)
                val fadeIn = AlphaAnimation(0f, 1f)
                fadeIn.duration = 500
                fadeIn.setAnimationListener(fadeInAnimationListener)
                v.ivTag.startAnimation(fadeIn)
            }
        }
    }


    val fadeInAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            Handler().postDelayed({
                if (context != null) {
                    v.ivTag.image = ContextCompat.getDrawable(context!!, R.drawable.ic_loading)
                    startAnimations()
                }
            }, 1000)
        }
    }



}



