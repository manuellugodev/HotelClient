package com.manuellugodev.hotelmanagment.data.sources

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.manuellugodev.hotelmanagment.LoginStatus
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