package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.LoginStatus

class FirebaseLogin:LoginDataSource {
    override suspend fun loginWithEmailAndPassword(email: String, password: String):LoginStatus {
        return LoginStatus.Success("Manuel")
    }


}