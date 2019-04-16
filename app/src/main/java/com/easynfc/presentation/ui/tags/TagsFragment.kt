package com.easynfc.presentation.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.model.Tags
import instanceOf
import kotlinx.android.synthetic.main.fragment_tags.view.*
import kotlinx.android.synthetic.main.item_tags.view.*
import org.jetbrains.anko.imageResource


class TagsFragment : BaseFragment() {

    private lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<TagsFragment>()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_tags, container, false)
        setupUI()
        return v
    }

    fun setupUI(){
        val views = arrayOf(v.text_layout, v.url_layout, v.sms_layout, v.phone_layout, v.launcher_layout, v.location_layout, v.wifi_layout, v.email_layout, v.format_layout, v.lock_layout)
        Tags.getList().forEachIndexed { index, element ->
            views[index].txt_type.text = element.name
            views[index].img_type.imageResource = element.drawable
        }
    }
}
