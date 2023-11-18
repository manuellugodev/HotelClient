package com.manuellugodev.hotelmanagment.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Date

fun convertLongToTime(time: Long): String {
    if (time === 0L) return ""

    val date = Date(time)
    val format = SimpleDateFormat("E, MMM dd")
    return format.format(date)
}

fun convertLongToDateTimeRoom(time:Long): String {
    val date=Date(time)
    val format = SimpleDateFormat("dd MMM yyyy hh:mm")
    return format.format(date)
}


