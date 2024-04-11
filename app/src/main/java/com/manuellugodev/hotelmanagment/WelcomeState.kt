package com.manuellugodev.hotelmanagment

sealed class WelcomeState {
    object IsLogged : WelcomeState()
    object Loading : WelcomeState()
    class Error(val message: String) : WelcomeState()
}