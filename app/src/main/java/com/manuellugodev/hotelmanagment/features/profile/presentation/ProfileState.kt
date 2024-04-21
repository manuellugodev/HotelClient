package com.manuellugodev.hotelmanagment.features.profile.presentation

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile

sealed class ProfileState {
    class ShowProfile(val profile: Profile) : ProfileState()
    class Error(val message: String) : ProfileState()
    object LoadingData : ProfileState()
    object init : ProfileState()

    object logOut : ProfileState()
}