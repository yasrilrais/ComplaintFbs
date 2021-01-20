package com.example.complaintfbs

object Const {
    val PATH_COLLECTION = "keluhan"
    val PATH_TGL = "strTgl"

    fun setTimeStamp(): Long {
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}