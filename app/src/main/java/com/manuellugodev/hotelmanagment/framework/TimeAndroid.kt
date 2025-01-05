package com.manuellugodev.hotelmanagment.framework

import com.manuellugodev.hotelmanagment.features.core.domain.TimeProvider
import java.util.Date

class TimeAndroid : TimeProvider {
    override fun getNowTime(): Date {
        return Date(System.currentTimeMillis())
    }
}