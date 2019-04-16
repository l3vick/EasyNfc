package com.easynfc.presentation.ui.menu.mytags


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf


class MyTagsFragment : BaseFragment() {

    lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<MyTagsFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_my_tags, container, false)

        return v
    }


}
