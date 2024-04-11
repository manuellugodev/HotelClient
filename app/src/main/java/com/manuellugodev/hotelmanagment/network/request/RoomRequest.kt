package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.TokenProvider
import com.manuellugodev.hotelmanagment.network.service.RoomService

class RoomRequest(baseUrl: String, tokenProvider: TokenProvider) :
    BaseRequest<RoomService>(baseUrl, tokenProvider) {

    val service = getService<RoomService>()
}