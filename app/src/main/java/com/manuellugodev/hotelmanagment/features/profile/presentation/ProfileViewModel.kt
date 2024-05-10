package com.manuellugodev.hotelmanagment.features.profile.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.profile.usecase.DoLogOutSession
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.domain.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDataProfile: GetDataProfile,
    private val doLogOutSession: DoLogOutSession
) :
    ViewModel() {

    var stateProfile: MutableState<ProfileState> = mutableStateOf(ProfileState.init)
        private set


    init {
        loadDataProfile()
    }

    fun loadDataProfile() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.Main) { getDataProfile() }
                if (result is DataResult.Success) {

                    stateProfile.value = ProfileState.ShowProfile(result.data)
                } else {
                    stateProfile.value = ProfileState.Error("Some was wrong")
                }
            } catch (e: Exception) {
                stateProfile.value =
                    ProfileState.Error(e.message ?: "Some was wrong getting profile")

            }


        }
    }

    fun logOutSession() {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.Main) { doLogOutSession() }

                if (result.isSuccess) {
                    stateProfile.value = ProfileState.logOut
                } else {
                    stateProfile.value = ProfileState.Error("Error log Out")
                }
            } catch (e: Exception) {
                stateProfile.value = ProfileState.Error("Error log Out")

            }
        }

    }


}