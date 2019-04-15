package com.easynfc.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.ui.category.CategoryFragment
import com.easynfc.presentation.ui.menu.mytags.MyTagsFragment
import com.easynfc.presentation.ui.read.ReadFragment
import com.easynfc.presentation.ui.tags.TagsFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_menu.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MenuFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.btnWrite.onClick {
            (activity as BaseActivity).display{ CategoryFragment.newInstance()}
        }

        view.btnRead.onClick {
            (activity as MainActivity).getNavigator().startReadActivity()
        }

        view.btnMyTags.onClick {
            (activity as BaseActivity).display{ MyTagsFragment.newInstance()}
        }

        return view
    }

    companion object {
        fun newInstance() = instanceOf<MenuFragment>()
    }



}
