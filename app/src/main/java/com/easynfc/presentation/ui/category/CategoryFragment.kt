package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.write.WriteFragment
import com.vipera.onepay.util.AppConstants
import instanceOf
import kotlinx.android.synthetic.main.fragment_category.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class CategoryFragment : BaseFragment() {

    lateinit var v: View

    companion object {

        val TAG: String = this.javaClass.canonicalName

        fun newInstance() = instanceOf<CategoryFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_category, container, false)
        setupUI()
        return v
    }

    private fun setupUI(){

        v.btnText.onClick {
            toast("navigating to Write")
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_text))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_TEXT))
        }

        v.btnContact.onClick {
            toast("navigating to Contact")
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_contact))
            (activity as MainActivity).replaceFragmentViewPager(ContactFragment.newInstance())
        }

        v.btnUtils.onClick {
            toast("navigating to Utils")
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_utils))
            (activity as MainActivity).replaceFragmentViewPager(UtilsFragment.newInstance())
        }

        v.btnClean.onClick {
            toast("navigating to Clean")
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_clean))
            (activity as MainActivity).replaceFragmentViewPager(CleanFragment.newInstance())
        }

    }

}
