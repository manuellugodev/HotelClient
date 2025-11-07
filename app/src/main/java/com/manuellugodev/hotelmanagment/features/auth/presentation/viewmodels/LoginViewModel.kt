package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.auth.domain.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.features.core.domain.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginWithUsernameAndPassword, private val dispatcher: DispatcherProvider) : ViewModel() {

    private val _statusLogin: MutableStateFlow<LoginStatus> = MutableStateFlow(LoginStatus())
    val statusLogin =_statusLogin.asStateFlow()


    private fun tryLogin() {
        _statusLogin.value = statusLogin.value.copy(showLoader = true)

        viewModelScope.launch(dispatcher.io) {
            val result = useCase(statusLogin.value.usernameEnter, statusLogin.value.passwordEnter)

            if(result.isSuccess){
                _statusLogin.value = _statusLogin.value.copy(showLoader = false, loginSuccess = true)
            }else{
                val exception = result.exceptionOrNull()

                _statusLogin.value =
                    _statusLogin.value.copy(showLoader = false, showError = exception)

            }
        }

    }

    fun onEvent(event: LoginEvent){
        when(event){
            LoginEvent.DoLoginEvent -> tryLogin()
            is LoginEvent.OnPasswordEnter -> {_statusLogin.value=statusLogin.value.copy(passwordEnter = event.password)}
            is LoginEvent.OnUsernameEnter -> {_statusLogin.value =statusLogin.value.copy(usernameEnter = event.username)}
            is LoginEvent.VisibilityPassword ->{_statusLogin.value=statusLogin.value.copy(showPassword = event.isVisible)}
            LoginEvent.DismissError -> {
                _statusLogin.value = _statusLogin.value.copy(showError = null)
            }
        }
    }

}