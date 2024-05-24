package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.auth.domain.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var useCase: LoginWithUsernameAndPassword,private val distpatcher: DistpatcherProvider) : ViewModel() {

    private val _statusLogin: MutableStateFlow<LoginStatus> = MutableStateFlow(LoginStatus())
    val statusLogin =_statusLogin.asStateFlow()


    private fun tryLogin() {
        _statusLogin.value = statusLogin.value.copy(showLoader = true)

        viewModelScope.launch(distpatcher.io) {
            val result = useCase(statusLogin.value.usernameEnter, statusLogin.value.passwordeEnter)

            if(result.isSuccess){
                _statusLogin.value = _statusLogin.value.copy(showLoader = false, loginSuccess = true)
            }else{
                val exception = result.exceptionOrNull()
                _statusLogin.value = _statusLogin.value.copy(showLoader = false, showError =exception?.message?:"Something is Wrong")
            }
        }

    }

    fun onEvent(event: LoginEvent){
        when(event){
            LoginEvent.doLoginEvent -> tryLogin()
            is LoginEvent.onPasswordEnter -> {_statusLogin.value=statusLogin.value.copy(passwordeEnter = event.password)}
            is LoginEvent.onUsernameEnter -> {_statusLogin.value =statusLogin.value.copy(usernameEnter = event.username)}
            is LoginEvent.visibilityPassword ->{_statusLogin.value=statusLogin.value.copy(showPassword = event.isVisible)}
        }
    }

    fun byDefault() {
        _statusLogin.value = statusLogin.value.copy(showError = "")
    }
}