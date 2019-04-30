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
import com.vipera.onepay.util.AppUtils

class TagsAdapter : RecyclerView.Adapter<TagsAdapter.TagHolder>() {

    private var baseList: MutableList<BaseTag> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.tag_item, parent, false)
        return TagHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        val tag = baseList[position]
        holder.tvAlias.text = tag.content
        holder.tvDate.text = tag.date?.let { AppUtils.toDate(it) }
        holder.ivType.background = tag.type?.let { getDrawable(it) }
    }

    override fun getItemCount(): Int {
        return baseList.size
    }

    fun setTags(list: List<BaseTag>) {
        if (this.baseList.isEmpty()) {
            this.baseList = list as MutableList<BaseTag>
        } else {
            this.baseList.addAll(list)
        }
        orderList()
        notifyDataSetChanged()
    }

    private fun getDrawable(type: String): Drawable? {
        when (type) {
            AppConstants.TYPE_TEXT -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_text)
            AppConstants.TYPE_EMAIL -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_write)
            AppConstants.TYPE_URL -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_clean)
            AppConstants.TYPE_PHONE -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_mobile)
            AppConstants.TYPE_LAUNCHER -> return ContextCompat.getDrawable(App.instance, R.drawable.ic_code)
        }
        return null
    }

    private fun orderList(){
        baseList.sortBy { it.date }
        baseList.reverse()
    }

    fun clear(){
        this.baseList.clear()
    }

    inner class TagHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAlias: TextView = itemView.findViewById(R.id.txtAlias)
        var tvDate: TextView = itemView.findViewById(R.id.txtDate)
        var ivType: ImageView = itemView.findViewById(R.id.ivType)
    }
}