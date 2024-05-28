package com.manuellugodev.hotelmanagment.features.profile.presentation

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile

data class ProfileState(
    val showProfile :Profile? = null,
    val showError:String ="",
    val showLoader:Boolean=false,
    val isLogOut:Boolean=false,
    val getDataProfile:Boolean=false
)