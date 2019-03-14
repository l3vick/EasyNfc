package com.easynfc.ui.main.tags


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.databinding.FragmentTagsBinding
import com.easynfc.model.Tags


class TagsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentTagsBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_tags, container,false)
        var tags = Tags.getList(context!!)
        binding.list = tags
        return binding.root
    }


    companion object {

        val TAG = this::class.java.name

        fun newInstance(): TagsFragment {
            return TagsFragment()
        }
    }

}
