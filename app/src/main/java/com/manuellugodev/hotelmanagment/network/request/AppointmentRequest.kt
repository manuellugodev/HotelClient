package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.service.AppointmentService

class AppointmentRequest(baseUrl: String, username: String, password: String) :
    BaseRequest<AppointmentService>(baseUrl, username, password) {

    val service = getService<AppointmentService>()
}