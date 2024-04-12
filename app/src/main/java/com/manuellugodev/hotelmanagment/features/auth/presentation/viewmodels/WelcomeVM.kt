package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.auth.domain.CheckUserIsLogged
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.features.auth.utils.WelcomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WelcomeVM @Inject constructor(var checkUserIsLoggedUseCase: CheckUserIsLogged) : ViewModel() {

    var stateWelcome: MutableState<WelcomeState?> = mutableStateOf(WelcomeState.Loading)

    fun checkUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = checkUserIsLoggedUseCase()

            withContext(Dispatchers.Main) {
                if (result is LoginStatus.Success) {
                    stateWelcome.value = WelcomeState.IsLogged
                } else {
                    stateWelcome.value = WelcomeState.Error("Bad Credentials")
                }
            }

        }
    }
}