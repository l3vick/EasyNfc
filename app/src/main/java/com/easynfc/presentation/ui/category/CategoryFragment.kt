package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.adapter.MenuPagerAdapter
import com.easynfc.presentation.ui.read.ReadFragment
import com.easynfc.presentation.ui.write.WriteFragment
import com.vipera.onepay.util.AppConstants
import instanceOf
import kotlinx.android.synthetic.main.fragment_category.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryFragment : BaseFragment() {

    lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<CategoryFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_category, container, false)
        setupUI()
        return v
    }

    fun setupUI(){

        v.btnText.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_write))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
        }

        v.btnContact.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_write))
            (activity as MainActivity).replaceFragmentViewPager(ContactFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
        }

        v.btnUtils.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_write))
            (activity as MainActivity).replaceFragmentViewPager(UtilsFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
        }

        v.btnClean.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_write))
            (activity as MainActivity).replaceFragmentViewPager(CleanFragment.newInstance(), AppConstants.FIRST_TAB_POSITION)
        }

    }


}
