package com.manuellugodev.hotelmanagment.firebase.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import kotlinx.coroutines.tasks.await

class FirebaseLogin(val auth: FirebaseAuth) : LoginDataSource {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): LoginStatus {

        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return LoginStatus.Success(result.user?.email?: "Bienvenido")
        } catch (e: Exception) {
            Log.e("Auth", e.message ?: "error authentication")
            return LoginStatus.Failure
        }
    }


}