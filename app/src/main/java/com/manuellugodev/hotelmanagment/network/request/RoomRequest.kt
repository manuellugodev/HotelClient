package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.service.RoomService

class RoomRequest(baseUrl:String):BaseRequest<RoomService> (baseUrl){

    val service = getService<RoomService>()
}