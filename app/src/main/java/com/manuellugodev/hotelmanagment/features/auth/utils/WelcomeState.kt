package com.manuellugodev.hotelmanagment.features.auth.utils

sealed class WelcomeState {
    object IsLogged : WelcomeState()
    object Loading : WelcomeState()
    class Error(val message: String) : WelcomeState()
}