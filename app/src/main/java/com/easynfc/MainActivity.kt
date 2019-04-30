package com.easynfc

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import com.easynfc.presentation.component.custom.CustomViewPager
import com.easynfc.presentation.ui.category.CleanFragment
import com.easynfc.presentation.ui.category.ContactFragment
import com.easynfc.presentation.ui.category.UtilsFragment
import com.easynfc.presentation.ui.write.WriteFragment
import com.vipera.onepay.util.AppConstants
import kotlinx.android.synthetic.main.custom_tab.view.*
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager


class MainActivity : BaseActivity() {

    override val contentLayoutResId = R.layout.activity_main

    lateinit var nfcManager: NfcManager
    private lateinit var mPager: CustomViewPager
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


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tagData = nfcManager.proccessIntent(intent!!)
        if (tagData != null) {
            showToolbar(getString(R.string.toolbar_title_read))
            replaceFragmentViewPager(ReadFragment.newInstance(tagData), AppConstants.SECOND_TAB_POSITION)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> navigateFragmentBack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            val fragment = getFragment<BaseFragment>(adapter, mPager)
            if (fragment is WriteFragment) {
                fragment.resetHint()
            }
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return true
    }

    private fun navigateFragmentBack() {
        when (getFragment<BaseFragment>(adapter, mPager)) {
            is ContactFragment, is UtilsFragment, is CleanFragment -> navigateToCategory()
            is ReadFragment -> navigateToLoading()
            is WriteFragment -> navBackFromWriter()
        }
    }

    fun replaceFragmentViewPager(fragment: Fragment, position: Int) {
        adapter.replaceFragment(position, fragment)
        adapter.notifyDataSetChanged()
        setupTabLayout()
    }

    private fun navigateToCategory() {
        hideToolbar()
        replaceFragmentViewPager(CategoryFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
    }


    private fun navigateToLoading() {
        hideToolbar()
        replaceFragmentViewPager(LoadingFragment.newInstance(), AppConstants.SECOND_TAB_POSITION)
    }

    private fun navigateToContacts(){
        showToolbar(getString(R.string.toolbar_title_contact))
        replaceFragmentViewPager(ContactFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
    }

    private fun navigateToUtils(){
        showToolbar(getString(R.string.toolbar_title_utils))
        replaceFragmentViewPager(UtilsFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
    }

    private fun navigateToClean(){
        showToolbar(getString(R.string.toolbar_title_clean))
        replaceFragmentViewPager(CleanFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
    }

    private fun navBackFromWriter(){
        when (WriteFragment.type){
            AppConstants.TYPE_TEXT -> navigateToCategory()
            AppConstants.TYPE_EMAIL -> navigateToContacts()
            AppConstants.TYPE_PHONE -> navigateToContacts()
            AppConstants.TYPE_SMS -> navigateToContacts()
            AppConstants.TYPE_URL -> navigateToUtils()
            AppConstants.TYPE_WIFI -> navigateToUtils()
            AppConstants.TYPE_LOCATION -> navigateToUtils()
            AppConstants.TYPE_LAUNCHER -> navigateToUtils()
            AppConstants.TYPE_FORMAT -> navigateToClean()
            AppConstants.TYPE_LOCK -> navigateToClean()
        }
    }


    private fun setupUI() {
        nfcManager = NfcManager(this)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.empty)

        mPager = pager

        mPager.swipeEnabled = false

        adapter.addFrag(CategoryFragment(), getString(R.string.first_tab_title))
        adapter.addFrag(LoadingFragment(), getString(R.string.second_tab_title))
        adapter.addFrag(MyTagsFragment(), getString(R.string.third_tab_title))

        mPager.adapter = adapter

        tabLayout.setupWithViewPager(mPager)

        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.gray_material_50), ContextCompat.getColor(this, R.color.subu_end))

        setupTabLayout()

        mPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (toolbar.isVisible) hideToolbar()

                if (position != AppConstants.FIRST_TAB_POSITION) {
                    replaceFragmentViewPager(CategoryFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
                }

                if (position != AppConstants.SECOND_TAB_POSITION) {
                    replaceFragmentViewPager(LoadingFragment.newInstance(), AppConstants.SECOND_TAB_POSITION)
                }

                if (position == AppConstants.THIRD_TAB_POSITION) {
                    showToolbarWithMenu(getString(R.string.toolbar_title_mytags))
                }
            }
        })
    }

    private fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun showToolbar(title: String) {
        toolbar.title = title
        toolbar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.menu.clear()
        //updateToolbarImage(title)
    }

    fun showToolbarWithMenu(title: String) {
        toolbar.title = title
        toolbar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar.inflateMenu(R.menu.menu)
    }

    private fun updateToolbarImage(title: String) {
        val imageView = toolbar.findViewById(R.id.ivToolbar) as ImageView
        when (title) {
            getString(R.string.toolbar_title_read) -> imageView.background = ContextCompat.getDrawable(this, R.drawable.ic_read)
            getString(R.string.toolbar_title_write) -> imageView.background = ContextCompat.getDrawable(this, R.drawable.ic_write)
        }
    }

    private fun setupTabLayout() {
        val writeTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout
        val readTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout
        val myTagsTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout

        writeTab.txtTab.text = getString(R.string.first_tab_title)
        writeTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_write)
        tabLayout.getTabAt(AppConstants.FIRST_TAB_POSITION)!!.customView = writeTab

        readTab.txtTab.text = getString(R.string.second_tab_title)
        readTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_read)
        tabLayout.getTabAt(AppConstants.SECOND_TAB_POSITION)!!.customView = readTab

        myTagsTab.txtTab.text = getString(R.string.third_tab_title)
        myTagsTab.imgTab.background = ContextCompat.getDrawable(this, R.drawable.ic_my_tags)
        tabLayout.getTabAt(AppConstants.THIRD_TAB_POSITION)!!.customView = myTagsTab
    }

}