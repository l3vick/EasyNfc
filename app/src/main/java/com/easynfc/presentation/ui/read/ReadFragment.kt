package com.easynfc.presentation.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.model.Tag
import com.easynfc.presentation.component.adapter.MenuPagerAdapter
import com.easynfc.presentation.ui.loading.LoadingFragment
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
        fun newInstance(tag: Tag) = instanceOf<ReadFragment>(Pair(AppConstants.TAG_DATA_ID, tag))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_read, container, false)
        setupUI()
        return v
    }

    override fun onResume() {
        val tagData = arguments?.get(AppConstants.TAG_DATA_ID)
        if (tagData != null) showTagData(tagData as Tag) else (activity as MainActivity).replaceFragmentViewPager(LoadingFragment.newInstance(), AppConstants.SECOND_TAB_POSITION)
        super.onResume()
    }

    fun showTagData(tag: Tag?) {
        if (tag != null) {
            contentTxt.text = tag.content
            typeTxt.text = tag.type
            tagTxt.text = tag.techList[0]
            techTxt.text = tag.techList[1]
            tnfTxt.text = tag.tnf
            rtdTxt.text = tag.rtd
            sizeTxt.text = tag.size
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
