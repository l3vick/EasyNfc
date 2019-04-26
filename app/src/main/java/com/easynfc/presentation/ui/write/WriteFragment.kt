package com.easynfc.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.easynfc.R
import com.easynfc.data.model.Text
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.custom.AutoFitEditText
import com.easynfc.presentation.viewmodel.TagsViewModel
import com.vipera.onepay.util.AppConstants
import instanceOf
import kotlinx.android.synthetic.main.fragment_read.view.*
import kotlinx.android.synthetic.main.fragment_write.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import com.vipera.onepay.util.AppUtils


class WriteFragment : BaseFragment() {

    private lateinit var v: View
    private lateinit var textContent: AutoFitEditText
    private lateinit var type: String

    private lateinit var tagsViewModel: TagsViewModel

    companion object {
        fun newInstance(type: String) = instanceOf<WriteFragment>(Pair(AppConstants.TAG_TYPE, type))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_write, container, false)
        setupUI()
        v.btnSave.onClick {
            insertTag()
        }
        return v
    }

    private fun setupUI(){
        type = arguments?.get(AppConstants.TAG_TYPE) as String
        textContent = v.etContent
        tagsViewModel = ViewModelProviders.of(this).get(TagsViewModel::class.java)
    }

    private fun insertTag(){
        when (type){
            AppConstants.TYPE_TEXT -> tagsViewModel.insert(Text(textContent.text.toString(), AppUtils.getCurDate()))
        }
    }

    fun resetHint() {
        textContent.hint = getString(R.string.et_writer_hint)
        textContent.isCursorVisible = false
    }
}
