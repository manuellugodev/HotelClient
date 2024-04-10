package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.service.RoomService

class RoomRequest(baseUrl: String, username: String, password: String) :
    BaseRequest<RoomService>(baseUrl, username, password) {

    val service = getService<RoomService>()
}