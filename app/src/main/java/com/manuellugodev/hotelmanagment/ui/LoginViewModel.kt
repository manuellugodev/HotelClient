package com.manuellugodev.hotelmanagment.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.LoginRepositoryImpl
import com.manuellugodev.hotelmanagment.data.sources.FirebaseLogin
import com.manuellugodev.hotelmanagment.usecases.LoginWithUsernameAndPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var useCase:LoginWithUsernameAndPassword) : ViewModel() {

     val _statusLogin :MutableState<LoginStatus> = mutableStateOf(LoginStatus.Pending)

     fun tryLogin(email: String, password: String) {
         _statusLogin.value=LoginStatus.Pending
        //val useCase = LoginWithUsernameAndPassword(LoginRepositoryImpl(FirebaseLogin(FirebaseAuth.getInstance())))

         viewModelScope.launch(Dispatchers.IO) {
            val result = useCase(email, password)

             _statusLogin.value=result
         }

    }
}