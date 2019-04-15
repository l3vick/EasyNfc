package com.easynfc.presentation.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.model.TagData
import com.easynfc.presentation.ui.loading.LoadingFragment
import com.vipera.onepay.util.AppConstants
import instanceOf
import kotlinx.android.synthetic.main.fragment_read.view.*


class ReadFragment : BaseFragment() {

    lateinit var contentTxt: TextView
    lateinit var typeTxt: TextView
    lateinit var tagTxt: TextView
    lateinit var techTxt: TextView
    lateinit var tnfTxt: TextView
    lateinit var rtdTxt: TextView
    lateinit var sizeTxt: TextView


    companion object {
        fun newInstance() = instanceOf<ReadFragment>()
        fun newInstance(tagData: TagData) = instanceOf<ReadFragment>(Pair(AppConstants.TAG_DATA_ID, tagData))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_read, container, false)
        setupUI(view)
        return view
    }

    override fun onResume() {
        val tagData = arguments?.get(AppConstants.TAG_DATA_ID)
        if (tagData != null) showTagData(tagData as TagData) else (activity as BaseActivity).display { LoadingFragment.newInstance() }
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

    fun setupUI(view: View) {
        contentTxt = view.txtContent
        typeTxt = view.txtTypeValue
        tagTxt = view.txtTagValue
        techTxt = view.txtTechValue
        tnfTxt = view.txtTnfValue
        rtdTxt = view.txtRtdValue
        sizeTxt = view.txtSizeValue
    }

}
