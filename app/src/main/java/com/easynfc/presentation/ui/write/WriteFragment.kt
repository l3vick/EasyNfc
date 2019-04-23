package com.easynfc.presentation.ui.write


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easynfc.R
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.custom.AutoFitEditText
import instanceOf
import kotlinx.android.synthetic.main.fragment_write.view.*


class WriteFragment : BaseFragment() {

    private lateinit var v: View
    private lateinit var textContent: AutoFitEditText

    companion object {
        fun newInstance() = instanceOf<WriteFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_write, container, false)
        setupUI()
        return v
    }

    private fun setupUI(){
        textContent = v.etContent
        textContent.maxHeight = 200
        textContent.minHeight = 16
    }

}
