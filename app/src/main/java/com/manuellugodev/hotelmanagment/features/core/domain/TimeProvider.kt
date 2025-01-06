package com.manuellugodev.hotelmanagment.features.core.domain

import java.util.Date

interface TimeProvider {

    fun getNowTime(): Date
}