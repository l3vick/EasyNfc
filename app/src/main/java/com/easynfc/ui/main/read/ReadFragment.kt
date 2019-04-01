package com.easynfc.ui.main.read


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.easynfc.R
import com.easynfc.showFragment
import com.easynfc.ui.main.loading.LoadingFragment


class ReadFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_read, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        activity!!.supportFragmentManager.showFragment<LoadingFragment>(ReadFragment.TAG, R.id.container)
    }

    companion object {
        val TAG = ReadFragment::class.java.name
    }
}
