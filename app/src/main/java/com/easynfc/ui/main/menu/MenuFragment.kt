package com.easynfc.ui.main.menu


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R
import com.easynfc.getFragment
import com.easynfc.show
import com.easynfc.ui.main.menu.mytags.MyTagsFragment
import com.easynfc.ui.main.read.ReadFragment
import com.easynfc.ui.main.tags.TagsFragment
import com.vipera.onepay.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_menu.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.btnWrite.onClick {
            show(activity!!.supportFragmentManager!!, activity!!.supportFragmentManager!!.getFragment<TagsFragment>(), TagsFragment.TAG , R.id.container)
        }

        view.btnRead.onClick {
            show(activity!!.supportFragmentManager!!, activity!!.supportFragmentManager!!.getFragment<ReadFragment>(), ReadFragment.TAG , R.id.container)
        }

        view.btnMyTags.onClick {
            show(activity!!.supportFragmentManager!!, activity!!.supportFragmentManager!!.getFragment<MyTagsFragment>(), MyTagsFragment.TAG , R.id.container)
        }

        return view
    }

    companion object {

        val TAG = MenuFragment::class.java.name

        fun newInstance (): MenuFragment {
            return MenuFragment()
        }
    }

}
