package com.easynfc.presentation.ui.menu

import android.content.Context
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.component.adapter.MenuPagerAdapter
import com.easynfc.presentation.ui.category.CategoryFragment
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.startActivity
import com.google.android.material.tabs.TabLayout


class MenuActivity : BaseActivity() {

    override val contentLayoutResId: Int = R.layout.activity_menu

    private lateinit var mPager: ViewPager
    private lateinit var mTabLayout: TabLayout

    companion object {
        fun start(context: Context) {
            context.startActivity<MenuActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mPager = pager as ViewPager

        mTabLayout = tablayout

        tablayout.setupWithViewPager(mPager)

        val adapter = MenuPagerAdapter(supportFragmentManager)


        adapter.addFrag(CategoryFragment(), "Tab1")
        adapter.addFrag(CategoryFragment(), "Tab2")
        adapter.addFrag(CategoryFragment(), "Tab3")

        mPager.setAdapter(adapter)
    }

}
