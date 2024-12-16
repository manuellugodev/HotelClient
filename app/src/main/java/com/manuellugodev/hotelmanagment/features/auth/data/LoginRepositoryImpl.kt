package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.domain.UserCredentials
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val loginLocalSource: TokenManagment
) : LoginRepository {
    override suspend fun doLogin(email: String, password: String): Result<Unit> {

        val result = loginDataSource.loginWithEmailAndPassword(email, password)

        if (result.isSuccess ) {
            val credentials = result.getOrDefault(UserCredentials("", 0))
            loginLocalSource.saveToken(credentials.token)
            return loginLocalSource.saveUsernameAndGuestId(email, credentials.guestId)
        }
        return Result.failure(result.exceptionOrNull()?:Exception())
    }

    override suspend fun doLogOut(): Result<Unit> {

        val result = loginLocalSource.removeToken() && loginLocalSource.removeUsername()

        if (result) {
            return Result.success(Unit)
        } else {
            return Result.failure(Exception("Error when remove token or username"))
        }
    }

    override suspend fun checkUserIsLogged(): Result<Unit>{
        return if (loginLocalSource.tokenIsAvailable()) {
            Result.success(Unit)
        } else {
            Result.failure(Exception())
        }
    }

    override suspend fun registerNewUser(userRegisterModel: UserRegisterModel): Result<Unit> {
        return loginDataSource.registerNewUser(userRegisterModel)
    }


}