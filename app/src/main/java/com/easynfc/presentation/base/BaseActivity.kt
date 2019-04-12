package com.easynfc.presentation.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.easynfc.R
import com.easynfc.navigation.NavigationExecutor
import com.easynfc.presentation.AppNavigator


abstract class BaseActivity : AppCompatActivity() {

    protected abstract val contentLayoutResId: Int?

    private val executor = NavigationExecutor(this)

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState).also {
        contentLayoutResId?.let { setContentView(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun replaceContainer(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.transaction { replace(R.id.container, fragment) }
    }

    protected fun addToContainer(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.transaction { add(R.id.container, fragment) }
    }

    inline fun <reified T : BaseFragment> display(createFragment: () -> T) =
            getFragment() ?: createFragment().also { replaceContainer(it) }

    inline fun <reified T : BaseFragment> displayOnTop(createFragment: () -> T) =
            getFragment() ?: createFragment().also { addToContainer(it) }

    protected inline fun <reified T : BaseFragment> getFragment(): T? =
            supportFragmentManager.findFragmentById(R.id.container) as? T

    fun getNavigator (): AppNavigator {
        return AppNavigator(executor)
    }

}