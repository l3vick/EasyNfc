package com.easynfc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.component.adapter.MenuPagerAdapter
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.easynfc.presentation.ui.menu.mytags.MyTagsFragment
import com.easynfc.presentation.ui.read.ReadFragment
import com.easynfc.utils.NfcManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.category.ContactFragment
import kotlinx.android.synthetic.main.custom_tab.view.*


class MainActivity : BaseActivity() {

    override val contentLayoutResId = R.layout.activity_main

    lateinit var nfcManager: NfcManager
    private lateinit var mPager: ViewPager
    private val adapter = MenuPagerAdapter(supportFragmentManager)


    companion object {
        fun start(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }


    fun replaceFragmentViewPager(fragment: Fragment, position: Int) {
        adapter.replaceFragment(position, fragment)
        adapter.notifyDataSetChanged()
        setupTabLayout()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager.proccessIntent(intent!!)
        if (tagData != null) {
            showToolbar()
            replaceFragmentViewPager(ReadFragment.newInstance(tagData), MenuPagerAdapter.SECOND_TAB_POSITION)
        }
    }

    override fun onBackPressed() {

        val i = 1

        when (getFragment<BaseFragment>(mPager)){
            is ContactFragment ->  replaceFragmentViewPager(CategoryFragment.newInstance(), MenuPagerAdapter.FIRST_TAB_POSITION)
        }

        super.onBackPressed()
    }


    private fun setupUI() {
        nfcManager = NfcManager(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = getString(R.string.empty)

        mPager = pager as ViewPager

        adapter.addFrag(CategoryFragment(), getString(R.string.first_tab_title))
        adapter.addFrag(LoadingFragment(), getString(R.string.second_tab_title))
        adapter.addFrag(MyTagsFragment(), getString(R.string.third_tab_title))

        mPager.setAdapter(adapter)

        tabLayout.setupWithViewPager(mPager)

        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.gray_material_50), ContextCompat.getColor(this, R.color.subu_end))

        setupTabLayout()

        mPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position != MenuPagerAdapter.SECOND_TAB_POSITION) {
                    replaceFragmentViewPager(LoadingFragment.newInstance(), MenuPagerAdapter.SECOND_TAB_POSITION)
                }
                if (position != MenuPagerAdapter.FIRST_TAB_POSITION) {
                    replaceFragmentViewPager(CategoryFragment.newInstance(), MenuPagerAdapter.FIRST_TAB_POSITION)
                }
            }
        })
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    private fun setupTabLayout() {
        val writeTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout
        val readTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout
        val myTagsTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout

        readTab.txtTab.text = getString(R.string.first_tab_title) //tab label txt
        readTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_read)
        tabLayout.getTabAt(MenuPagerAdapter.FIRST_TAB_POSITION)!!.customView = readTab

        writeTab.txtTab.text = getString(R.string.second_tab_title) //tab label txt
        writeTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_write)
        tabLayout.getTabAt(MenuPagerAdapter.SECOND_TAB_POSITION)!!.customView = writeTab

        myTagsTab.txtTab.text = getString(R.string.third_tab_title) //tab label txt
        myTagsTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_my_tags)
        tabLayout.getTabAt(MenuPagerAdapter.THIRD_TAB_POSITION)!!.customView = myTagsTab
    }

}