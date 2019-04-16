package com.easynfc.presentation.component.animation

import android.view.animation.Animation
import android.view.animation.TranslateAnimation

class LTRViewAnimation(fromXDelta: Float = -300.0f, toXDelta: Float = 0.0f, fromYDelta: Float = 0.0f, toYDelta: Float = 0.0f) : TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta) {

   init {
       this.duration = 1000
       this.repeatCount = 0
       this.fillAfter = false
   }

}