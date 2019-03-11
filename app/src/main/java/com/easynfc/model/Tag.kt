package com.easynfc.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.easynfc.R


class Tags {
    companion object {
        fun getList(context: Context) :  List<Tag> {
            return listOf(Tag("text", ContextCompat.getDrawable(context, R.drawable.ic_mytags)!!),Tag("url", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("sms", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("phone", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("launcher", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("location", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("wifi", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("email", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("format", ContextCompat.getDrawable(context, R.drawable.ic_read)!!),Tag("lock", ContextCompat.getDrawable(context, R.drawable.ic_read)!!))
        }
    }
}

data class Tag (
        var name: String,
        var drawable: Drawable
)

