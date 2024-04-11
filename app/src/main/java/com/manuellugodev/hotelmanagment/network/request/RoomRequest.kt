package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.data.sources.TokenManagment
import com.manuellugodev.hotelmanagment.network.BaseRequest

import com.manuellugodev.hotelmanagment.network.service.RoomService

class RoomRequest(baseUrl: String, tokenManagment: TokenManagment) :
    BaseRequest<RoomService>(baseUrl, tokenManagment) {

    val service = getService<RoomService>()
}