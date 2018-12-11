package com.easynfc.ui.tags


import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.adapter.DataBindingAdapters
import com.easynfc.databinding.FragmentTagsBinding


class TagsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTagsBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_tags, container,false)

        return binding.root
    }


    companion object {

        val TAG = this::class.java.name

        fun newInstance(): TagsFragment {
            return TagsFragment()
        }
    }

}
