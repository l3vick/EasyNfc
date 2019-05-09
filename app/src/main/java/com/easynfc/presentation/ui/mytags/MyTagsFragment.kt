package com.easynfc.presentation.ui.menu.mytags


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easynfc.MainActivity
import com.easynfc.R
import com.easynfc.data.model.*
import com.easynfc.data.source.TagsRepository
import com.easynfc.presentation.base.BaseFragment
import com.easynfc.presentation.component.adapter.TagsAdapter
import com.easynfc.presentation.component.custom.CustomLifeCycleOwner
import com.easynfc.presentation.viewmodel.TagsViewModel
import com.easynfc.presentation.viewmodel.TagsViewModelFactory
import instanceOf
import org.jetbrains.anko.appcompat.v7.coroutines.onMenuItemClick
import org.jetbrains.anko.support.v4.toast


class MyTagsFragment : BaseFragment() {

    lateinit var v: View
    private lateinit var tagsViewModel: TagsViewModel
    private val adapter = TagsAdapter()
    private val customLifeCycleOwner = CustomLifeCycleOwner()

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

        tagsViewModel = ViewModelProviders.of(this, TagsViewModelFactory(TagsRepository(context!!))).get(TagsViewModel::class.java)

        getTags()

        (activity as MainActivity).mToolbar.onMenuItemClick {
            when (it!!.itemId){
                R.id.btnDeleteAll -> toast("sort")
                R.id.btnOrderBy -> tagsViewModel.deleteAll()
            }
        }
    }

    private fun getTags(){

        tagsViewModel.getAllText().observe(customLifeCycleOwner,
                Observer<List<Text>> {
                    t -> adapter.setTags(t!!)
                })
        tagsViewModel.getAllEmail().observe(customLifeCycleOwner,
                Observer<List<Email>> {
                    t -> adapter.setTags(t!!)
                    tagsViewModel.getAllEmail().removeObservers(customLifeCycleOwner)
                })
        tagsViewModel.getAllUrl().observe(customLifeCycleOwner,
                Observer<List<Url>> {
                    t -> adapter.setTags(t!!)
                    tagsViewModel.getAllUrl().removeObservers(customLifeCycleOwner)
                })
        tagsViewModel.getAllPhone().observe(customLifeCycleOwner,
                Observer<List<Phone>> {
                    t -> adapter.setTags(t!!)
                    tagsViewModel.getAllPhone().removeObservers(customLifeCycleOwner)
                })
        tagsViewModel.getAllLauncher().observe(customLifeCycleOwner,
                Observer<List<Launcher>> {
                    t -> adapter.setTags(t!!)
                    tagsViewModel.getAllLauncher().removeObservers(customLifeCycleOwner)
                })
    }

    override fun onPause() {
        adapter.clear()
        super.onPause()
    }

    override fun onResume() {
        adapter.setTags(tagsViewModel.getData())
        super.onResume()
    }
}
