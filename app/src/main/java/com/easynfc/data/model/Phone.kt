package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "text")
data class Phone(var content: String, var date: String, var type: String = AppConstants.TYPE_TEXT) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}