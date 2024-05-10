package com.manuellugodev.hotelmanagment.framework.network.source

import android.net.http.HttpException
import android.util.Log
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.framework.network.entities.LoginRequestBody
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
}