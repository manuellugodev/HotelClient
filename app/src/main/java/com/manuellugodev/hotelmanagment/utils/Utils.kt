package com.manuellugodev.hotelmanagment.utils

import java.text.SimpleDateFormat
import java.util.Date

fun convertLongToTime(time: Long): String {
    if (time === 0L) return ""

    val date = Date(time)
    val format = SimpleDateFormat("E, MMM dd")
    return format.format(date)
}