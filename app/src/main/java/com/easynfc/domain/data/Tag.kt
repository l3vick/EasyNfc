package com.easynfc.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.easynfc.R


class Tags {
    companion object {
        fun getList(context: Context) :  List<Tag> {
            return listOf(Tag("text", ContextCompat.getDrawable(context, R.drawable.ic_text)!!),Tag("url", ContextCompat.getDrawable(context, R.drawable.ic_url)!!),Tag("sms", ContextCompat.getDrawable(context, R.drawable.ic_sms)!!),Tag("phone", ContextCompat.getDrawable(context, R.drawable.ic_phone)!!),Tag("launcher", ContextCompat.getDrawable(context, R.drawable.ic_launcher)!!),Tag("location", ContextCompat.getDrawable(context, R.drawable.ic_location)!!),Tag("wifi", ContextCompat.getDrawable(context, R.drawable.ic_wifi)!!),Tag("email", ContextCompat.getDrawable(context, R.drawable.ic_mail)!!),Tag("format", ContextCompat.getDrawable(context, R.drawable.ic_remove)!!),Tag("lock", ContextCompat.getDrawable(context, R.drawable.ic_lock)!!))
        }
    }
}

data class Tag (
        var name: String,
        var drawable: Drawable
)


