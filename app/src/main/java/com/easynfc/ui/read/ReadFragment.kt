package com.easynfc.ui.read


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R


class ReadFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read, container, false)


        return view
    }



    companion object {

        val TAG = ReadFragment::class.java.name

        fun newInstance (): ReadFragment {
            return ReadFragment()
        }
    }
}
