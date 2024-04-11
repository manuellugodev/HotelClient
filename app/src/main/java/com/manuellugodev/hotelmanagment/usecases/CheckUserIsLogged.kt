package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.LoginRepository

class CheckUserIsLogged(private val repository: LoginRepository) {

    suspend operator fun invoke(): LoginStatus {
        return repository.checkUserIsLogged()
    }
}