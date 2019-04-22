package com.easynfc.presentation.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.model.TagData
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.easynfc.presentation.ui.menu.MenuActivity
import com.vipera.onepay.util.AppConstants
import instanceOf
import kotlinx.android.synthetic.main.fragment_read.view.*


class ReadFragment : BaseFragment() {

    private lateinit var v: View
    private lateinit var contentTxt: TextView
    private lateinit var typeTxt: TextView
    private lateinit var tagTxt: TextView
    private lateinit var techTxt: TextView
    private lateinit var tnfTxt: TextView
    private lateinit var rtdTxt: TextView
    private lateinit var sizeTxt: TextView


    companion object {
        fun newInstance() = instanceOf<ReadFragment>()
        fun newInstance(tagData: TagData) = instanceOf<ReadFragment>(Pair(AppConstants.TAG_DATA_ID, tagData))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_read, container, false)
        setupUI()
        return v
    }

    override fun onResume() {
        val tagData = arguments?.get(AppConstants.TAG_DATA_ID)
        if (tagData != null) showTagData(tagData as TagData) else (activity as MenuActivity).replaceFragmentViewPager(LoadingFragment.newInstance(),"",1)
        super.onResume()
    }

    fun showTagData(tagData: TagData?) {
        if (tagData != null) {
            contentTxt.text = tagData.content
            typeTxt.text = tagData.type
            tagTxt.text = tagData.techList[0]
            techTxt.text = tagData.techList[1]
            tnfTxt.text = tagData.tnf
            rtdTxt.text = tagData.rtd
            sizeTxt.text = tagData.size
        }
    }

    fun setupUI() {
        contentTxt = v.txtContent
        typeTxt = v.txtTypeValue
        tagTxt = v.txtTagValue
        techTxt = v.txtTechValue
        tnfTxt = v.txtTnfValue
        rtdTxt = v.txtRtdValue
        sizeTxt = v.txtSizeValue
    }

}
