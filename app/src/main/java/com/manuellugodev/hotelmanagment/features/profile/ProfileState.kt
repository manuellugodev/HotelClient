package com.manuellugodev.hotelmanagment.features.profile

sealed class ProfileState {
    class ShowProfile(val name: String) : ProfileState()
    class Error(val message: String) : ProfileState()
    object LoadingData : ProfileState()
    object init : ProfileState()

    object logOut : ProfileState()
}