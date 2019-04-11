package com.easynfc

import android.content.Intent
import android.os.Bundle
import com.easynfc.base.BaseActivity
import com.easynfc.ui.main.menu.MenuFragment
import com.easynfc.ui.main.read.ReadFragment
import com.easynfc.util.NfcManager


class MainActivity : BaseActivity() {

    var nfcManager : NfcManager? = null

    override val contentLayoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcManager = NfcManager(this)
        display { MenuFragment.newInstance() }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager?.proccessIntent(intent!!)
        display { ReadFragment.newInstance() }
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is ReadFragment) {
            fragment.showTagData(tagData)
        }
    }
}