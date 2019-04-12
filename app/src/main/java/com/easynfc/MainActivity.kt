package com.easynfc

import android.content.Context
import android.os.Bundle
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.menu.MenuFragment
import com.easynfc.presentation.ui.menu.mytags.MyTagsFragment
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    override val contentLayoutResId = R.layout.activity_main

    companion object {
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        display { MenuFragment.newInstance() }
    }

    override fun onBackPressed() {
        var fragment = getFragment<BaseFragment>()
        when (fragment ){
            is CategoryFragment, is MyTagsFragment -> display { MenuFragment.newInstance() }
        }
    }



}