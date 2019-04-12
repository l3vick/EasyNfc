package com.easynfc.navigation

import android.app.Activity
import android.content.Context
import java.lang.ref.WeakReference

class NavigationExecutor(context: Context) {

    private var context: WeakReference<Context> = WeakReference(context)
    private var pendingNavigation: ((Context) -> Unit)? = null

    fun request(navigation: ((Context) -> Unit)) {
        pendingNavigation = navigation
        executePendingNavigation()
    }

    fun setActivityContext(activity: Activity) {
        context = WeakReference(activity)
        executePendingNavigation()
    }

    fun removeActivityContext(activity: Activity) {
        if (context.get() == activity) {
            context = WeakReference<Context>(null)
        }
    }

    private fun executePendingNavigation() {
        context.get()?.let {
            pendingNavigation?.invoke(it)
            pendingNavigation = null
        }
    }
}
