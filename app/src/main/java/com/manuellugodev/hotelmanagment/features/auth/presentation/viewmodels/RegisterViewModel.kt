package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor():ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onEvent(event:RegisterEvent){
        when(event){
            is RegisterEvent.onEmailEnter -> state=state.copy(email = event.email)
            is RegisterEvent.onFirstNameEnter -> state =state.copy(firstName = event.firstName)
            is RegisterEvent.onLastNameEnter -> state = state.copy(lastName = event.lastName)
            is RegisterEvent.onPasswordEnter -> state = state.copy(password = event.password)
            is RegisterEvent.onPhoneEnter -> state =state.copy(phone = event.phone)
            is RegisterEvent.onUsernameEnter -> state = state.copy(username = event.username)
            is RegisterEvent.isPasswordVisible -> state =state.copy(isShowingPassword =event.visible )
            RegisterEvent.submitDataUser -> sendDataUser()

        }
    }

    private fun sendDataUser() {

        state=state.copy(navigateToLogin = true)
    }

}
