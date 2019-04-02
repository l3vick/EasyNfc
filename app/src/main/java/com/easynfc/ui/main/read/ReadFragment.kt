package com.easynfc.ui.main.read

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easynfc.R
import com.easynfc.model.TagData
import com.easynfc.showFragment
import com.easynfc.ui.main.loading.LoadingFragment
import kotlinx.android.synthetic.main.fragment_read.view.*
import org.jetbrains.anko.support.v4.toast


class ReadFragment : Fragment() {

    lateinit var contentTxt: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_read, container, false)
        activity!!.supportFragmentManager.showFragment<LoadingFragment>(LoadingFragment.TAG, R.id.container)
        contentTxt = view.txtContent
        return  view
    }

    fun showTagData(tagData: TagData?) {
        if (tagData != null) {
            contentTxt.text = tagData.content
        }
    }


    companion object {
        val TAG = ReadFragment::class.java.name
    }
}
