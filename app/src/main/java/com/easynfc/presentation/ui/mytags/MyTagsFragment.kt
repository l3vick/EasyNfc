package com.easynfc.presentation.ui.menu.mytags


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easynfc.R
import com.easynfc.data.model.*
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.adapter.TagsAdapter
import com.easynfc.presentation.viewmodel.TagsViewModel
import instanceOf


class MyTagsFragment : BaseFragment() {

    lateinit var v: View
    private lateinit var tagsViewModel: TagsViewModel
    private val adapter = TagsAdapter()

    companion object {
        fun newInstance() = instanceOf<MyTagsFragment>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_my_tags, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        tagsViewModel = ViewModelProviders.of(this).get(TagsViewModel::class.java)
    }

    private fun getTags(){
        tagsViewModel.getAllText().observe(this,
                Observer<List<Text>> { t -> adapter.setTags(t!!) })
        tagsViewModel.getAllEmail().observe(this,
                Observer<List<Email>> { t -> adapter.setTags(t!!) })
        tagsViewModel.getAllUrl().observe(this,
                Observer<List<Url>> { t -> adapter.setTags(t!!) })
        tagsViewModel.getAllPhone().observe(this,
                Observer<List<Phone>> { t -> adapter.setTags(t!!) })
        tagsViewModel.getAllLauncher().observe(this,
                Observer<List<Launcher>> { t -> adapter.setTags(t!!) })
    }

    override fun onPause() {
        adapter.clear()
        super.onPause()
    }

    override fun onResume() {
        getTags()
        super.onResume()
    }
}
