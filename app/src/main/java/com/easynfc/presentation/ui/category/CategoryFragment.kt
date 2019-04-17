package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseActivity
import com.easynfc.presentation.base.BaseFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_category.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryFragment : BaseFragment() {

    lateinit var v: View

    companion object {
        fun newInstance() = instanceOf<CategoryFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_category, container, false)
        setupUI()
        return v
    }

    fun setupUI(){

        v.btnText.onClick {
            //(activity as BaseActivity).display{ CategoryFragment.newInstance()}
        }

        v.btnContact.onClick {
            (activity as BaseActivity).display{ ContactFragment.newInstance()}
        }

        v.btnUtils.onClick {
            (activity as BaseActivity).display{ UtilsFragment.newInstance()}
        }

        v.btnClean.onClick {
            (activity as BaseActivity).display{ CleanFragment.newInstance()}
        }

    }


}
