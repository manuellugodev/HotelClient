package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.service.AppointmentService

class AppointmentRequest(baseUrl:String): BaseRequest<AppointmentService>(baseUrl) {

    val service=getService<AppointmentService>()
}