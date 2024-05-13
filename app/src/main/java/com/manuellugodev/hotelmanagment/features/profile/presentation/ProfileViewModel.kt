package com.manuellugodev.hotelmanagment.features.profile.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.profile.usecase.DoLogOutSession
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDataProfile: GetDataProfile,
    private val doLogOutSession: DoLogOutSession
) :ViewModel() {

    private val _stateProfile :MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val stateProfile :StateFlow<ProfileState> = _stateProfile

    init {
        loadDataProfile()
    }

    fun loadDataProfile() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) { getDataProfile() }
                if (result.isSuccess) {

                    _stateProfile.value=_stateProfile.value.copy(showProfile = result.getOrThrow())
                } else {

                    _stateProfile.value=_stateProfile.value.copy(showError = "Some was wrong")
                }
            } catch (e: Exception) {
                _stateProfile.value = _stateProfile.value.copy(showError = e.message ?: "Some was wrong getting profile")

            }


        }
    }

    fun logOutSession() {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) { doLogOutSession() }

                if (result.isSuccess) {
                    _stateProfile.value = stateProfile.value.copy(isLogOut = true)
                } else {
                    _stateProfile.value =stateProfile.value.copy(showError = "Error log out")
                }
            } catch (e: Exception) {
                _stateProfile.value =stateProfile.value.copy(showError = "Error log out")

            }
        }

    }


}