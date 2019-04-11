package com.easynfc.ui.main.menu.mytags


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.base.BaseFragment
import instanceOf


class MyTagsFragment :BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_my_tags, container, false)

        return view
    }


    companion object {
        fun newInstance() = instanceOf<MyTagsFragment>()
        val TAG = MyTagsFragment::class.java.name
    }
}
