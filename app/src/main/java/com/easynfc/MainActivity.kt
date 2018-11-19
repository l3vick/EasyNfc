package com.easynfc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.easynfc.ui.menu.MenuFragment
import com.vipera.onepay.util.AppConstants
import com.vipera.onepay.util.FragmentUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentUtils.show(supportFragmentManager, MenuFragment.TAG , R.id.container,null)
    }
}
