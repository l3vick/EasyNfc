package com.easynfc.presentation.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easynfc.R
import com.easynfc.data.model.Text

class TagsAdapter: RecyclerView.Adapter<TagsAdapter.NoteHolder>() {

    private var textList: List<Text> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.tag_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = textList[position]
        holder.textViewTitle.text = currentNote.content
        holder.textViewDescription.text = currentNote.timestamp.toString()
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    fun setTags(textList: List<Text>) {
        this.textList = textList
        notifyDataSetChanged()
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        var textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)

    }
}