package com.easynfc.presentation.ui.write


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf


class WriteFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_write, container, false)

        return view
    }

    companion object {
        fun newInstance() = instanceOf<WriteFragment>()
        val TAG = WriteFragment::class.java.name
    }

}
