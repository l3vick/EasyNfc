package com.easynfc.presentation.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import instanceOf
import kotlinx.android.synthetic.main.fragment_clean.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CleanFragment : BaseFragment() {

    private lateinit var v : View

    companion object {
        fun newInstance() = instanceOf<CleanFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_clean, container, false)
        setupUI()
        return v
    }

    fun setupUI(){
        v.btnFormat.onClick {
            notImplemented()
        }
        v.btnLock.onClick {
            notImplemented()
        }
    }

}
