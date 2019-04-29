package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "text")
data class Text(override var content: String, override var date: String, override var type: String = AppConstants.TYPE_TEXT) : BaseTag() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}