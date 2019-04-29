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
import kotlinx.android.synthetic.main.fragment_utils.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class UtilsFragment : BaseFragment() {

    private lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<UtilsFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_utils, container, false)
        setupUI()
        return v
    }

    fun setupUI() {

        v.btnUrl.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_url))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_URL), AppConstants.FIRST_TAB_POSITION)
        }
        v.btnWifi.onClick {
            notImplemented()
        }
        v.btnLocation.onClick {
            notImplemented()
        }
        v.btnLauncher.onClick {
            (activity as MainActivity).showToolbar(getString(R.string.toolbar_title_launcher))
            (activity as MainActivity).replaceFragmentViewPager(WriteFragment.newInstance(AppConstants.TYPE_LAUNCHER), AppConstants.FIRST_TAB_POSITION)
        }
    }

}
