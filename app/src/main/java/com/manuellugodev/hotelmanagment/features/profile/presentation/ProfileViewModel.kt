package com.manuellugodev.hotelmanagment.features.profile.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import com.manuellugodev.hotelmanagment.features.profile.domain.usecase.DoLogOutSession
import com.manuellugodev.hotelmanagment.features.profile.domain.usecase.GetDataProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDataProfile: GetDataProfile,
    private val doLogOutSession: DoLogOutSession,
    private val distpatcher: DistpatcherProvider
) :ViewModel() {

    private val _stateProfile :MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState(getDataProfile = true))
    val stateProfile :StateFlow<ProfileState> = _stateProfile

    private fun loadDataProfile() {
        viewModelScope.launch(distpatcher.main) {
            try {
                val result = withContext(distpatcher.io) { getDataProfile() }
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

    private fun logOutSession() {

        viewModelScope.launch(distpatcher.main) {
            try {
                val result = withContext(distpatcher.io) { doLogOutSession() }

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


    fun onEvent(event:ProfileEvent){
        when(event){
            ProfileEvent.LoadProfile -> loadDataProfile()
            ProfileEvent.LogOutSession -> logOutSession()
        }

    }

}