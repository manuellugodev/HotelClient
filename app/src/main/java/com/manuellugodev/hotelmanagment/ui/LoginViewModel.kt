package com.manuellugodev.hotelmanagment.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.LoginRepositoryImpl
import com.manuellugodev.hotelmanagment.data.sources.FirebaseLogin
import com.manuellugodev.hotelmanagment.usecases.LoginWithUsernameAndPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

     val _statusLogin :MutableState<LoginStatus> = mutableStateOf(LoginStatus.Pending)

     fun tryLogin(email: String, password: String) {
        val useCase = LoginWithUsernameAndPassword(LoginRepositoryImpl(FirebaseLogin()))

         viewModelScope.launch(Dispatchers.IO) {
            val result = useCase(email, password)

             _statusLogin.value=result
         }

    }
}