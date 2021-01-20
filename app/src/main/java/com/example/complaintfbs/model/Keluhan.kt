package com.example.complaintfbs.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Keluhan(
    var strId: String = "0",
    var strKeluhan: String? = null,
    var strTgl: String? = null
) : Parcelable