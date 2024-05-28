package com.manuellugodev.hotelmanagment.features.auth.utils
sealed class LoginEvent{
    data class OnUsernameEnter(val username:String):LoginEvent()

    data class OnPasswordEnter(val password:String):LoginEvent()

    data class VisibilityPassword(val isVisible:Boolean):LoginEvent()

    object DoLoginEvent:LoginEvent()

    object DismissError:LoginEvent()
}