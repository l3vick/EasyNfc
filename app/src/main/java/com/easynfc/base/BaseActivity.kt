package com.easynfc.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.easynfc.R


abstract class BaseActivity : AppCompatActivity() {

    protected abstract val contentLayoutResId: Int?

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState).also {
        contentLayoutResId?.let { setContentView(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun replaceContainer(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.transaction { replace(R.id.container, fragment) }
    }

    inline fun <reified T : BaseFragment> display(createFragment: () -> T) =
            getFragment() ?: createFragment().also { replaceContainer(it) }

    protected inline fun <reified T : BaseFragment> getFragment(): T? =
            supportFragmentManager.findFragmentById(R.id.container) as? T

}