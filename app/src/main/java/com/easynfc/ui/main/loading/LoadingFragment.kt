package com.easynfc.ui.main.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.base.BaseFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_loading.view.*


class LoadingFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_loading, container, false)
        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotation)
        v.ivLoading.startAnimation(rotation)
        return v
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).nfcManager?.disableForegroundDispatch()
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nfcManager?.enableForegroundDispatch()
    }


    companion object {
        fun newInstance() = instanceOf<LoadingFragment>()
        val TAG = LoadingFragment::class.java.name
    }
}
