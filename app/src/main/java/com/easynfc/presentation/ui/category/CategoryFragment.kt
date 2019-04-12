package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_category.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = instanceOf<CategoryFragment>()
        val TAG = CategoryFragment::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_category, container, false)

        view.btnText.onClick {

        }

        view.btnContact.onClick {

        }

        view.btnUtils.onClick {

        }

        view.btnClean.onClick {

        }

        return view
    }


}
