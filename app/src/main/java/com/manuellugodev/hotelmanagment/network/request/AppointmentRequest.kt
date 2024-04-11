package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.TokenProvider
import com.manuellugodev.hotelmanagment.network.service.AppointmentService

class AppointmentRequest(baseUrl: String, tokenProvider: TokenProvider) :
    BaseRequest<AppointmentService>(baseUrl, tokenProvider) {

    val service = getService<AppointmentService>()
}