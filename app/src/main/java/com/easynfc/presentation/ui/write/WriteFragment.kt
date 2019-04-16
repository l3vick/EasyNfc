package com.easynfc.presentation.ui.write


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf


class WriteFragment : BaseFragment() {

    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_write, container, false)

        return v
    }

    companion object {
        fun newInstance() = instanceOf<WriteFragment>()
    }

}
