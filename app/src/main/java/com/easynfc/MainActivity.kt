package com.easynfc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.easynfc.ui.main.menu.MenuFragment
import com.easynfc.ui.main.read.ReadFragment
import com.easynfc.util.NfcManager
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    var nfcManager : NfcManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcManager = NfcManager(this)
        supportFragmentManager.showFragment<MenuFragment>(MenuFragment.TAG, R.id.container)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager?.proccessIntent(intent!!)
        supportFragmentManager.showFragment<ReadFragment>(ReadFragment.TAG, R.id.container)
        val fragment = supportFragmentManager.findFragmentByTag(ReadFragment.TAG)
        if (fragment is ReadFragment) {
            fragment.showTagData(tagData)
        }
    }
}

inline fun <reified T : Fragment> FragmentManager.showFragment(tag: String, container: Int, args: Bundle? = null) {
    val clazz = T::class.java
    var fragment = findFragmentByTag(tag)
    if (fragment != null) {
        this.popBackStack(tag, 0)
    } else {
        fragment = (clazz.getConstructor().newInstance() as T).apply {
            args?.let {
                it.classLoader = javaClass.classLoader
                arguments = args
            }
        }
        val transaction = this.beginTransaction()
        transaction.add(container, fragment as Fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
}