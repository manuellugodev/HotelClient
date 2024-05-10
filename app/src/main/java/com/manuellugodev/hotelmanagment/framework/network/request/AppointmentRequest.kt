package com.manuellugodev.hotelmanagment.framework.network.request

import com.manuellugodev.hotelmanagment.domain.TokenManagment
import com.manuellugodev.hotelmanagment.framework.network.service.AppointmentService

class AppointmentRequest(baseUrl: String, tokenManagment: TokenManagment) :
    com.manuellugodev.hotelmanagment.framework.network.BaseRequest<AppointmentService>(baseUrl, tokenManagment) {

    val service = getService<AppointmentService>()
}