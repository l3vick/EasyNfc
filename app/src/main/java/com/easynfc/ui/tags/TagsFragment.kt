package com.easynfc.ui.tags


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R


class TagsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tags, container, false)

        return view
    }


    companion object {

        val TAG = this::class.java.name

        fun newInstance(): TagsFragment {
            return TagsFragment()
        }
    }

}
