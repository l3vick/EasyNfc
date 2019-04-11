package com.easynfc.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.base.BaseActivity
import com.easynfc.base.BaseFragment
import com.easynfc.ui.main.menu.mytags.MyTagsFragment
import com.easynfc.ui.main.read.ReadFragment
import com.easynfc.ui.main.tags.TagsFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.btnWrite.setOnClickListener {
            (activity as BaseActivity).display{ TagsFragment.newInstance()}
        }

        view.btnRead.setOnClickListener {
            (activity as BaseActivity).display{ ReadFragment.newInstance()}
        }

        view.btnMyTags.setOnClickListener {
            (activity as BaseActivity).display{ MyTagsFragment.newInstance()}
        }

        return view
    }

    companion object {
        fun newInstance() = instanceOf<MenuFragment>()
        val TAG = MenuFragment::class.java.name
    }



}
