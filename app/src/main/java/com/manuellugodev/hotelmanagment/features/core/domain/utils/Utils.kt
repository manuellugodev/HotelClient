package com.manuellugodev.hotelmanagment.features.core.domain.utils

import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertLongToTime(time: Long): String {
    if (time === 0L) return ""

    val date = Date(time)
    val format = SimpleDateFormat("E, MMM dd",)
    format.timeZone= TimeZone.getTimeZone("UTC")

    return format.format(date)
}

fun convertLongToDateTimeRoom(time: Long, pattern: String = "dd MMM yyyy"): String {
    val date=Date(time)
    val format = SimpleDateFormat(pattern)
    format.timeZone= TimeZone.getTimeZone("UTC")
    return format.format(date)
}

fun convertDateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    format.timeZone = TimeZone.getTimeZone("GMT")
    return format.format(date)
}

