package com.easynfc

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.easynfc.presentation.ui.menu.mytags.MyTagsFragment
import com.easynfc.presentation.ui.read.ReadFragment
import com.easynfc.utils.NfcManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import com.easynfc.presentation.ui.category.CleanFragment
import com.easynfc.presentation.ui.category.ContactFragment
import com.easynfc.presentation.ui.category.UtilsFragment
import com.easynfc.presentation.ui.write.WriteFragment
import com.vipera.onepay.util.AppConstants
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {

    override val contentLayoutResId = R.layout.activity_main

    lateinit var nfcManager: NfcManager
    lateinit var mToolbar: Toolbar

    private val categoryFragment = CategoryFragment.newInstance()
    private val readFragment = ReadFragment.newInstance()
    private val myTagsFragment = MyTagsFragment.newInstance()
    private val loadingFragment = LoadingFragment.newInstance()

    private lateinit var active: Fragment

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
            toast("navigating to onNewIntent")
            replaceFragmentViewPager(ReadFragment.newInstance(tagData))
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
           /* val fragment = getFragment<BaseFragment>(adapter, mPager)
            if (fragment is WriteFragment) {
                fragment.resetHint()
            }*/
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return true
    }

    private fun navigateFragmentBack() {
      /*  when (getFragment<BaseFragment>(adapter, mPager)) {
            is ContactFragment, is UtilsFragment, is CleanFragment -> navigateToCategory()
            is ReadFragment -> navigateToLoading()
            is WriteFragment -> navBackFromWriter()
        }*/
    }

    fun replaceFragmentViewPager(fragment: Fragment) {
        //toast("replacing fragment:" + fragment.javaClass.canonicalName)
        /*supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
        active = fragment*/
       /* adapter.replaceFragment(position, fragment)
        adapter.notifyDataSetChanged()
        setupTabLayout()*/

        supportFragmentManager.beginTransaction().hide(active).show(fragment).commit()
        active = fragment

    }

    private fun navigateToCategory() {
        hideToolbar()
        replaceFragmentViewPager(CategoryFragment.newInstance())
    }


    private fun navigateToLoading() {
        hideToolbar()
        replaceFragmentViewPager(LoadingFragment.newInstance())
    }

    private fun navigateToContacts(){
        showToolbar(getString(R.string.toolbar_title_contact))
        replaceFragmentViewPager(ContactFragment.newInstance())
    }

    private fun navigateToUtils(){
        showToolbar(getString(R.string.toolbar_title_utils))
        replaceFragmentViewPager(UtilsFragment.newInstance())
    }

    private fun navigateToClean(){
        showToolbar(getString(R.string.toolbar_title_clean))
        replaceFragmentViewPager(CleanFragment.newInstance())
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

        mToolbar = toolbar

        setSupportActionBar(mToolbar)

        mToolbar.title = getString(R.string.empty)

        supportFragmentManager.beginTransaction().add(R.id.container, myTagsFragment, MyTagsFragment.TAG).hide(myTagsFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, loadingFragment, LoadingFragment.TAG).hide(loadingFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, categoryFragment, CategoryFragment.TAG).commit()

        active = categoryFragment

        bottomNavigator.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.item_write -> {
                supportFragmentManager.beginTransaction().hide(active).show(categoryFragment).commit()
                active = categoryFragment
                return@OnNavigationItemSelectedListener true
            }

            R.id.item_read -> {
                supportFragmentManager.beginTransaction().hide(active).show(loadingFragment).commit()
                active = loadingFragment
                return@OnNavigationItemSelectedListener true
            }

            R.id.item_my_tags -> {
                supportFragmentManager.beginTransaction().hide(active).show(myTagsFragment).commit()
                active = myTagsFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun hideToolbar() {
        mToolbar.visibility = View.GONE
        mToolbar.menu.clear()
    }

    fun showToolbar(title: String) {
        mToolbar.title = title
        mToolbar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar.menu.clear()
        //updateToolbarImage(title)
    }

    fun showToolbarWithMenu(title: String) {
        mToolbar.title = title
        mToolbar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mToolbar.inflateMenu(R.menu.menu)
    }

    private fun updateToolbarImage(title: String) {
        val imageView = toolbar.findViewById(R.id.ivToolbar) as ImageView
        when (title) {
            getString(R.string.toolbar_title_read) -> imageView.background = ContextCompat.getDrawable(this, R.drawable.ic_read)
            getString(R.string.toolbar_title_write) -> imageView.background = ContextCompat.getDrawable(this, R.drawable.ic_write)
        }
    }

    private fun setupTabLayout() {
        /*val writeTab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as ConstraintLayout
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
        tabLayout.getTabAt(AppConstants.THIRD_TAB_POSITION)!!.customView = myTagsTab*/
    }

}