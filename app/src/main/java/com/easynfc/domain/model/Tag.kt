package com.easynfc.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.easynfc.R




class Tags {
    companion object {
        fun getList() :  List<Tag> {
            return listOf(Tag("text", R.drawable.ic_text, null),Tag("url",  R.drawable.ic_url, null),Tag("sms", R.drawable.ic_sms, null),Tag("phone", R.drawable.ic_phone, null),Tag("launcher", R.drawable.ic_launcher, null),Tag("location", R.drawable.ic_location, null),Tag("wifi", R.drawable.ic_wifi, null),Tag("email", R.drawable.ic_mail, null),Tag("format", R.drawable.ic_remove, null),Tag("lock",  R.drawable.ic_lock, null))
        }
    }
}

data class Tag (
        var name: String,
        var drawable: Int,
        var view: View?
)


data class TagData (
        var type: String,
        var content: String,
        var techList: Array<String>,
        var tnf: String,
        var rtd: String,
        var size: String
)