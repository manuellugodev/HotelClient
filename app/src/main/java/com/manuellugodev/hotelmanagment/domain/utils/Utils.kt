package com.manuellugodev.hotelmanagment.domain.utils

import java.text.SimpleDateFormat

import java.util.Date
import java.util.TimeZone

fun convertLongToTime(time: Long): String {
    if (time === 0L) return ""

    val date = Date(time)
    val format = SimpleDateFormat("E, MMM dd",)
    format.timeZone= TimeZone.getTimeZone("UTC")

    return format.format(date)
}

fun convertLongToDateTimeRoom(time:Long): String {
    val date=Date(time)
    val format = SimpleDateFormat("dd MMM yyyy")
    format.timeZone= TimeZone.getTimeZone("UTC")
    return format.format(date)
}


