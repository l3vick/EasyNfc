package com.easynfc.presentation.base

import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.toast

abstract class BaseFragment : Fragment() {

    fun notImplemented(){
        toast("Function not implemented")
    }

}