package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.auth.domain.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var useCase: LoginWithUsernameAndPassword) : ViewModel() {

    var statusLogin: MutableState<LoginStatus> = mutableStateOf(LoginStatus.Pending)
        private set

    fun tryLogin(email: String, password: String) {
        statusLogin.value = LoginStatus.Pending
        //val useCase = LoginWithUsernameAndPassword(LoginRepositoryImpl(FirebaseLogin(FirebaseAuth.getInstance())))

        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase(email, password)

            if(result.isSuccess){
                statusLogin.value = LoginStatus.Success("")
            }else{
                statusLogin.value = LoginStatus.Failure
            }
        }

    }

    fun byDefault() {
        statusLogin.value = LoginStatus.Pending
    }
}