package com.easynfc.ui.main.loading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.easynfc.R
import kotlinx.android.synthetic.main.fragment_loading.view.*


class LoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_loading, container, false)
        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotation)
        v.ivLoading.startAnimation(rotation)
        return v
    }

}
