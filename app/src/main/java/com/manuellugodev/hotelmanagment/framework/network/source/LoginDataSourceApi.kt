package com.manuellugodev.hotelmanagment.framework.network.source

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.domain.UserCredentials
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel
import com.manuellugodev.hotelmanagment.features.auth.domain.exceptions.AuthenticationFailedException
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.GeneralExceptionApp
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.UsernameAlreadyExist
import com.manuellugodev.hotelmanagment.framework.network.entities.ApiResponse
import com.manuellugodev.hotelmanagment.framework.network.entities.LoginRequestBody
import com.manuellugodev.hotelmanagment.framework.network.entities.SignUpRequestBody
import com.manuellugodev.hotelmanagment.framework.network.request.LoginRequest
import retrofit2.Response

class LoginDataSourceApi(private val request: LoginRequest) : LoginDataSource {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserCredentials> {
        try {

            FirebaseCrashlytics.getInstance().setCustomKey("Login_source_error", "LoginDataSource")

            val result = request.getService().doLogin(LoginRequestBody(email, password))
            if (result.isSuccessful) {

                val data = result.body()!!.data!!
                val userCredentials = UserCredentials(data.token, data.guestId)

                return Result.success(userCredentials)
            } else {

                val errorBody = result.errorBody()

                val raw = errorBody?.string()

                if (raw != null) {
                    try {
                        // Parse the error body into ServerResponse using Gson or Moshi
                        val gson = Gson()

                        val errorResponse: ApiResponse<*>? =
                            gson.fromJson(raw, ApiResponse::class.java)

                        // Access specific fields from the parsed error response
                        val errorType = errorResponse?.errorType
                        var errorMessage = errorResponse?.message

                        if (errorType == "IncorrectCredentials") {
                            return Result.failure(AuthenticationFailedException())
                        }

                        return Result.failure(GeneralExceptionApp("Some is wrong"))

                    } catch (e: Exception) {

                        FirebaseCrashlytics.getInstance()
                            .log("The code is trying interprete the exception")
                        FirebaseCrashlytics.getInstance().recordException(e)
                        return Result.failure(GeneralExceptionApp("Some is Wrong"))
                    }
                }

                val exception = GeneralExceptionApp(result.body()?.errorType)
                FirebaseCrashlytics.getInstance().log("The result was not success")
                FirebaseCrashlytics.getInstance().recordException(exception)

                return Result.failure(GeneralExceptionApp("Some is wrong"))
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Some failed trying solve the request")
            FirebaseCrashlytics.getInstance().recordException(e)
            return Result.failure(GeneralExceptionApp("Some is wrong"))
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

    private fun getException(response: Response<ApiResponse<Unit>>): Exception {
        val errorResponse = response.errorBody()?.string()

        val jsonObject: JsonObject = JsonParser.parseString(errorResponse).asJsonObject

        val error = jsonObject.get("errorType").asString ?: ""

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