package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf


class UtilsFragment : BaseFragment() {

    private lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<UtilsFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_utils, container, false)
        setupUI()
        return v
    }

    fun setupUI(){

    }

}
