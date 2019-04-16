package com.easynfc.presentation.ui.read

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.easynfc.utils.NfcManager
import kotlinx.android.synthetic.main.activity_read.*
import org.jetbrains.anko.backgroundColor
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
        toolbar.title = ""
        toolbar.backgroundColor =  ContextCompat.getColor(this, android.R.color.transparent)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        display { ReadFragment.newInstance() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when (getFragment<BaseFragment>()){
            is ReadFragment ->  display { LoadingFragment.newInstance() }
            is LoadingFragment -> getNavigator().startMainActivity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager?.proccessIntent(intent!!)
        display {
            ReadFragment.newInstance(tagData!!)
        }
    }
}
