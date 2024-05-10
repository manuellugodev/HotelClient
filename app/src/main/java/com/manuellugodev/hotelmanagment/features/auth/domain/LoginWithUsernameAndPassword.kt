package com.manuellugodev.hotelmanagment.features.auth.domain


import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus

class LoginWithUsernameAndPassword(private val repository: LoginRepository) {

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.doLogin(email, password)
    }
}