package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.LoginStatus

interface LoginDataSource {

    suspend fun loginWithEmailAndPassword(email:String,password:String):LoginStatus
}