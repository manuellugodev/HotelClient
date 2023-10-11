package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.LoginRepository

class LoginWithUsernameAndPassword(private val repository: LoginRepository) {

    suspend operator fun invoke(email:String,password:String):LoginStatus{
        return repository.doLogin(email, password)
    }
}