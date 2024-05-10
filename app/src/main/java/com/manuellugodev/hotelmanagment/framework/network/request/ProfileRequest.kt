package com.manuellugodev.hotelmanagment.framework.network.request

import com.manuellugodev.hotelmanagment.framework.network.BaseRequest
import com.manuellugodev.hotelmanagment.domain.TokenManagment
import com.manuellugodev.hotelmanagment.framework.network.service.ProfileService

class ProfileRequest(baseUrl: String, token: TokenManagment) :
    com.manuellugodev.hotelmanagment.framework.network.BaseRequest<ProfileService>(baseUrl, token) {

    val service = getService<ProfileService>()
}