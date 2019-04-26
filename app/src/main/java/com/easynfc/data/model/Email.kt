package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "email")
data class Email(var content: String, var date: String, var type: String = AppConstants.TYPE_EMAIL) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}