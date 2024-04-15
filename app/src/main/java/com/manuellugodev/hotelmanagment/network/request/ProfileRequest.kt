package com.manuellugodev.hotelmanagment.network.request

import com.manuellugodev.hotelmanagment.network.BaseRequest
import com.manuellugodev.hotelmanagment.network.TokenManagment
import com.manuellugodev.hotelmanagment.network.service.ProfileService

class ProfileRequest(baseUrl: String, token: TokenManagment) :
    BaseRequest<ProfileService>(baseUrl, token) {

    val service = getService<ProfileService>()
}