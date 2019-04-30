package com.easynfc.data.model

abstract class BaseTag {
    abstract val content: String?
    abstract val date: Long?
    abstract val id: Int?
    abstract val type: String?
}