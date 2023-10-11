package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {
    override suspend fun doLogin(email: String, password: String): LoginStatus {
        return loginDataSource.loginWithEmailAndPassword(email, password)
    }
}