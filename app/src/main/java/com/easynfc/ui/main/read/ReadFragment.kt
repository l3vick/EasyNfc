package com.easynfc.ui.main.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easynfc.R
import com.easynfc.base.BaseActivity
import com.easynfc.base.BaseFragment
import com.easynfc.model.TagData
import com.easynfc.ui.main.loading.LoadingFragment
import com.easynfc.ui.main.tags.TagsFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_read.view.*


class ReadFragment : BaseFragment() {

    lateinit var contentTxt: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_read, container, false)
        contentTxt = view.txtContent
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity).display{ LoadingFragment.newInstance()}
    }

    fun showTagData(tagData: TagData?) {
        if (tagData != null) {
            contentTxt.text = tagData.content
        }
    }


    companion object {
        fun newInstance() = instanceOf<ReadFragment>()
        val TAG = ReadFragment::class.java.name
    }
}
