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
import org.jetbrains.anko.support.v4.toast


class ContactFragment : BaseFragment() {

    private lateinit var v : View

    companion object {
        fun newInstance() = instanceOf<ContactFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_contact, container, false)
        setupUI()
        return v
    }

    private fun setupUI(){
        v.btnEmail.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_email))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_EMAIL))
        }
        v.btnPhone.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_phone))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_PHONE))
        }
        v.btnSms.onClick {
            notImplemented()
        }
    }

}
