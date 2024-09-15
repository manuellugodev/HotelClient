package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.auth.domain.DoSignUpUseCase
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterState
import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.UsernameAlreadyExist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val doSignUpUsecase: DoSignUpUseCase,
    private val distpatcher: DistpatcherProvider
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.onEmailEnter -> {
                verifyEmail(event)
            }

            is RegisterEvent.onFirstNameEnter -> state = state.copy(firstName = event.firstName)
            is RegisterEvent.onLastNameEnter -> state = state.copy(lastName = event.lastName)
            is RegisterEvent.onPasswordEnter -> state = state.copy(password = event.password)
            is RegisterEvent.onPhoneEnter -> state = state.copy(phone = event.phone)
            is RegisterEvent.onUsernameEnter -> {
                state = state.copy(username = event.username, usernameError = "")
            }

            is RegisterEvent.isPasswordVisible -> state =
                state.copy(isShowingPassword = event.visible)

            RegisterEvent.submitDataUser -> sendDataUser()
            RegisterEvent.dismissError -> state = state.copy(msgError = "")
        }
    }

    private fun sendDataUser() {

        val username = state.username
        val password = state.password
        val firstName = state.firstName
        val lastName = state.lastName
        val email = state.email
        val phone = state.phone

        val userToRegister =
            UserRegisterModel(username, password, firstName, lastName, email, phone)

        if (isPresentError()) {
            state = state.copy(msgError = "Check the fields")
            return
        }
        viewModelScope.launch(distpatcher.io) {

            val result = doSignUpUsecase(userToRegister)

            if (result.isSuccess) {
                state = state.copy(navigateToLogin = true)
            } else {
                val exception = result.exceptionOrNull()

                when (exception) {

                    is UsernameAlreadyExist -> {
                        state = state.copy(usernameError = "Username Already Exist")

                    }

                    else -> {
                        state = state.copy(msgError = "Some was wrong")

                    }
                }
            }

        }


    }

    private fun verifyEmail(event: RegisterEvent.onEmailEnter) {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        val emailIsNotValid = event.email.matches(emailPattern).not()
        if (emailIsNotValid) {
            state = state.copy(email = event.email, emailError = event.email)
        } else {
            state = state.copy(email = event.email, emailError = "")
        }

    }

    private fun isPresentError(): Boolean {
        return if (state.usernameError.isNotEmpty() || state.emailError.isNotEmpty() || state.passwordError.isNotEmpty() || state.msgError.isNotEmpty()) {
            true
        } else {
            false
        }
    }


}
