package com.easynfc.presentation.ui.read

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.easynfc.presentation.ui.menu.MenuFragment
import com.easynfc.utils.NfcManager
import org.jetbrains.anko.startActivity

class ReadActivity : BaseActivity() {

    lateinit var nfcManager: NfcManager

    override val contentLayoutResId: Int = R.layout.activity_read

    companion object {
        fun start(context: Context) {
            context.startActivity<ReadActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcManager = NfcManager(this)
        display { ReadFragment.newInstance() }
    }

    override fun onBackPressed() {
        var fragment = getFragment<BaseFragment>()
        when (fragment ){
            is ReadFragment -> getNavigator().startMainActivity()
            is LoadingFragment -> getNavigator().startMainActivity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager?.proccessIntent(intent!!)
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is ReadFragment) {
            fragment.showTagData(tagData)
        }
    }
}
