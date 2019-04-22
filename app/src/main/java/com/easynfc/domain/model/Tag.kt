package com.easynfc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag (
        var type: String,
        var content: String,
        var techList: Array<String>,
        var tnf: String,
        var rtd: String,
        var size: String
) : Parcelable
