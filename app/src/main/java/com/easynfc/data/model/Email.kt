package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "email")
data class Email(override var content: String, override var date: String, override var type: String = AppConstants.TYPE_EMAIL) : BaseTag() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}