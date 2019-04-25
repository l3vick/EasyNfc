package com.easynfc

import android.app.Application
import android.content.res.Resources

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        protected lateinit var instance: App

        val resources: Resources
            get() = instance.resources
    }
}