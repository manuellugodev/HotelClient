package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.TokenManagment
import com.manuellugodev.hotelmanagment.network.service.AppointmentService

class AppointmentRequest(baseUrl: String, tokenManagment: TokenManagment) :
    BaseRequest<AppointmentService>(baseUrl, tokenManagment) {

    val service = getService<AppointmentService>()
}