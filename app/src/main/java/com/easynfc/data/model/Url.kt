package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "url")
data class Url(override var content: String, override var date: Long, override var type: String = AppConstants.TYPE_URL) : BaseTag() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}