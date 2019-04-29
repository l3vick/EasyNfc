package com.easynfc.presentation.component.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easynfc.App
import com.easynfc.R
import com.easynfc.data.model.BaseTag
import com.vipera.onepay.util.AppConstants

class TagsAdapter: RecyclerView.Adapter<TagsAdapter.TagHolder>() {

    private var baseList: List<BaseTag> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.tag_item, parent, false)
        return TagHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        val tag = baseList[position]
        holder.tvAlias.text = tag.content
        holder.tvDate.text = tag.date
        holder.ivType.background = tag.type?.let { getDrawable(it) }
    }

    override fun getItemCount(): Int {
        return baseList.size
    }

    fun setTags(list: List<BaseTag>) {
        this.baseList = list
        notifyDataSetChanged()
    }

    private fun getDrawable(type : String): Drawable? {
        when (type){
            AppConstants.TYPE_TEXT -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_write)
            AppConstants.TYPE_EMAIL -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_text)
        }
        return null
    }

    inner class TagHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAlias: TextView = itemView.findViewById(R.id.txtAlias)
        var tvDate: TextView = itemView.findViewById(R.id.txtDate)
        var ivType: ImageView = itemView.findViewById(R.id.ivType)
    }
}