package com.manuellugodev.hotelmanagment.features.auth.domain

import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus

class CheckUserIsLogged(private val repository: LoginRepository) {

    suspend operator fun invoke(): Result<Unit> {
        return repository.checkUserIsLogged()
    }
}