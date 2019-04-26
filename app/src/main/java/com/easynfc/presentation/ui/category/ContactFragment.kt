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
import kotlinx.android.synthetic.main.fragment_contact.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class ContactFragment : BaseFragment() {

    private lateinit var v : View

    companion object {
        fun newInstance() = instanceOf<ContactFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_contact, container, false)
        setupUI()
        v.btnEmail.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_write))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_EMAIL), AppConstants.FIRST_TAB_POSITION)
        }
        return v
    }

    fun setupUI(){

    }

}
