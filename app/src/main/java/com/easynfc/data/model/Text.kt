package com.easynfc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text")
data class Text(var content: String, var timestamp: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}