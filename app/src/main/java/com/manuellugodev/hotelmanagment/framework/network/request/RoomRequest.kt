package com.manuellugodev.hotelmanagment.framework.network.request

import com.manuellugodev.hotelmanagment.framework.network.BaseRequest
import com.manuellugodev.hotelmanagment.domain.TokenManagment
import com.manuellugodev.hotelmanagment.framework.network.service.RoomService

class RoomRequest(baseUrl: String, tokenManagment: TokenManagment) :
    com.manuellugodev.hotelmanagment.framework.network.BaseRequest<RoomService>(baseUrl, tokenManagment) {

    val service = getService<RoomService>()
}