package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.network.TokenManagment
import com.manuellugodev.hotelmanagment.utils.DataResult

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

    override suspend fun doLogOut(): DataResult<Boolean> {

        val result = loginLocalSource.removeToken()

        if (result) {
            return DataResult.Success(result)
        } else {
            return DataResult.Error(Exception("Error when remove token"))
        }
    }

    override suspend fun checkUserIsLogged(): LoginStatus {
        return if (loginLocalSource.tokenIsAvailable()) {
            LoginStatus.Success("Logged")
        } else {
            LoginStatus.Failure
        }
    }


}