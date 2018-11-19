package com.easynfc.ui.menu


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R


class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)


        return view
    }

    companion object {

        val TAG = this::class.java.name

        fun newInstance (): MenuFragment {
            return MenuFragment()
        }
    }

}
