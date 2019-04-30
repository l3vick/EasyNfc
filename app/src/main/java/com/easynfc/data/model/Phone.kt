package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vipera.onepay.util.AppConstants

@Entity(tableName = "phone")
data class Phone(override var content: String, override var date: Long, override var type: String = AppConstants.TYPE_PHONE): BaseTag() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}