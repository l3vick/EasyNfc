package com.easynfc.presentation.component.custom

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LifecycleOwner


class CustomLifeCycleOwner : LifecycleOwner {

    private val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun stopListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun startListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun getLifecycle(): Lifecycle {
        Log.i("CustomLifeCycleOwner", "Returning registry!!")
        return mLifecycleRegistry
    }
}