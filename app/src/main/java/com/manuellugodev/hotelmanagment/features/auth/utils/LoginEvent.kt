package com.manuellugodev.hotelmanagment.features.auth.utils
sealed class LoginEvent{
    data class onUsernameEnter(val username:String):LoginEvent()

    data class onPasswordEnter(val password:String):LoginEvent()

    data class visibilityPassword(val isVisible:Boolean):LoginEvent()

    object doLoginEvent:LoginEvent()
}