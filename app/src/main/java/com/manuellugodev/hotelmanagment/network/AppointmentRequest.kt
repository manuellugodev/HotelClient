package com.manuellugodev.hotelmanagment.network

class AppointmentRequest(baseUrl:String):BaseRequest<AppointmentService>(baseUrl) {

    val service=getService<AppointmentService>()
}