package com.manuellugodev.hotelmanagment.features.auth.utils

sealed class RegisterEvent {

    data class onUsernameEnter(val username:String):RegisterEvent()

    data class onPasswordEnter(val password:String):RegisterEvent()

    data class onPasswordConfirmationEnter(val confimrationPassword: String) : RegisterEvent()

    data class onFirstNameEnter(val firstName:String):RegisterEvent()

    data class onLastNameEnter(val lastName:String):RegisterEvent()

    data class onEmailEnter(val email:String):RegisterEvent()

    data class onPhoneEnter(val phone:String):RegisterEvent()

    data class isPasswordVisible(val visible:Boolean):RegisterEvent()

    object submitDataUser : RegisterEvent()

    object dismissError : RegisterEvent()


}