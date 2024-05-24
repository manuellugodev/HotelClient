package com.manuellugodev.hotelmanagment.features.profile.presentation

sealed class ProfileEvent {


    object LoadProfile:ProfileEvent()
    object LogOutSession:ProfileEvent()
}