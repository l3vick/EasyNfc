package com.easynfc.ui.menu


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R
import com.easynfc.ui.mytags.MyTagsFragment
import com.easynfc.ui.read.ReadFragment
import com.easynfc.ui.tags.TagsFragment
import com.vipera.onepay.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_menu.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.btnWrite.onClick {
            FragmentUtils.show(activity!!.supportFragmentManager!!,TagsFragment.TAG, R.id.container, null)
        }

        view.btnRead.onClick {
            FragmentUtils.show(activity!!.supportFragmentManager!!,ReadFragment.TAG, R.id.container, null)
        }

        view.btnMyTags.onClick {
            FragmentUtils.show(activity!!.supportFragmentManager!!,MyTagsFragment.TAG, R.id.container, null)
        }

        return view
    }

    companion object {

        val TAG = this::class.java.name

        fun newInstance (): MenuFragment {
            return MenuFragment()
        }
    }

}
