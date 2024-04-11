package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource
import com.manuellugodev.hotelmanagment.data.sources.TokenManagment

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val loginLocalSource: TokenManagment
) : LoginRepository {
    override suspend fun doLogin(email: String, password: String): LoginStatus {

        val result = loginDataSource.loginWithEmailAndPassword(email, password)

        if (result is LoginStatus.Success) {
            return loginLocalSource.saveToken(result.data)
        }
        return result
    }

    override suspend fun checkUserIsLogged(): LoginStatus {
        return if (loginLocalSource.tokenIsAvailable()) {
            LoginStatus.Success("Logged")
        } else {
            LoginStatus.Failure
        }
    }


}