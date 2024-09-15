package com.manuellugodev.hotelmanagment.framework.network.source

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.UsernameAlreadyExist
import com.manuellugodev.hotelmanagment.framework.network.entities.LoginRequestBody
import com.manuellugodev.hotelmanagment.framework.network.entities.SignUpRequestBody
import com.manuellugodev.hotelmanagment.framework.network.request.LoginRequest
import retrofit2.Response

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

            result.code()

            if (result.isSuccessful) {
                Result.success(Unit)
            } else if (result.code() == 409) {

                val error = getException(result)
                Result.failure(error)
            } else {
                Result.failure(Exception("Some was wrong"))
            }
        } catch (e: Exception) {
            Result.failure(e)

        }

    }

    private fun UserRegisterModel.toRequestBody(): SignUpRequestBody {
        return SignUpRequestBody(userName, password, firstName, lastName, email, phone)
    }

    private fun getException(response: Response<Unit>): Exception {
        val errorResponse = response.errorBody()?.string()

        val jsonObject: JsonObject = JsonParser.parseString(errorResponse).asJsonObject

        val error = jsonObject.get("error").asString ?: ""

        return when (error) {
            "UsernameAlreadyExist" -> {
                UsernameAlreadyExist()
            }

            else -> {
                Exception("Some was Wrong")
            }
        }

    }
}