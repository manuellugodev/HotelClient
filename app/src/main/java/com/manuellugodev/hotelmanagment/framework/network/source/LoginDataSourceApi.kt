package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel
import com.manuellugodev.hotelmanagment.framework.network.entities.LoginRequestBody
import com.manuellugodev.hotelmanagment.framework.network.entities.SignUpRequestBody
import com.manuellugodev.hotelmanagment.framework.network.request.LoginRequest

class LoginDataSourceApi(private val request: LoginRequest) : LoginDataSource {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): Result<String> {
        try {

            val result = request.getService().doLogin(LoginRequestBody(email, password))
            if (result.isSuccessful) {


                return Result.success(result.body()!!.token)
            } else {
                return Result.failure(Exception())
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

    override suspend fun registerNewUser(userRegisterModel: UserRegisterModel): Result<Unit> {

        return try {
            val result = request.getService().signUpUser(userRegisterModel.toRequestBody())

            if (result.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Some was wrong when "))
            }
        } catch (e: Exception) {
            Result.failure(e)

        }

    }

    private fun UserRegisterModel.toRequestBody(): SignUpRequestBody {
        return SignUpRequestBody(userName, password, firstName, lastName, email, phone)
    }
}