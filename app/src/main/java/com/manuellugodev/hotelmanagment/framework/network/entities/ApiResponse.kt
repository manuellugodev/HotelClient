package com.manuellugodev.hotelmanagment.framework.network.entities

data class ApiResponse<T>(

    val data: T?,
    val status: Int,
    val message: String,
    val errorType: String,
    val timeStamp: Long
)