package com.manuellugodev.hotelmanagment.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.usecases.LoginWithUsernameAndPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var useCase:LoginWithUsernameAndPassword) : ViewModel() {

    var statusLogin: MutableState<LoginStatus> = mutableStateOf(LoginStatus.Pending)
        private set

    fun tryLogin(email: String, password: String) {
        statusLogin.value = LoginStatus.Pending
        //val useCase = LoginWithUsernameAndPassword(LoginRepositoryImpl(FirebaseLogin(FirebaseAuth.getInstance())))

        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase(email, password)

            statusLogin.value = result
        }

    }

    fun byDefault() {
        statusLogin.value = LoginStatus.Pending
    }
}