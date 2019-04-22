package com.easynfc.presentation.ui.menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.component.adapter.MenuPagerAdapter
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.menu.mytags.MyTagsFragment
import com.easynfc.presentation.ui.read.ReadFragment
import com.easynfc.utils.NfcManager
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.startActivity
import com.google.android.material.tabs.TabLayout


class MenuActivity : BaseActivity() {

    override val contentLayoutResId: Int = R.layout.activity_menu

    private lateinit var nfcManager: NfcManager
    private lateinit var mPager: ViewPager
    private lateinit var mTabLayout: TabLayout
    val adapter = MenuPagerAdapter(supportFragmentManager)

    companion object {
        fun start(context: Context) {
            context.startActivity<MenuActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcManager = NfcManager(this)

        mPager = pager as ViewPager

        mTabLayout = tablayout

        tablayout.setupWithViewPager(mPager)




        adapter.addFrag(CategoryFragment(), "Tab1")
        adapter.addFrag(ReadFragment(), "Tab2")
        adapter.addFrag(MyTagsFragment(), "Tab3")

        mPager.setAdapter(adapter)
    }


    fun replaceFragmentViewPager(fragment: Fragment, title: String, position: Int){
        adapter.replaceFragment(position,fragment, title)
        adapter.notifyDataSetChanged()
    }

}
