package com.manuellugodev.hotelmanagment.utils

import androidx.compose.runtime.MutableState

data class NumberGuest(
    var adults: MutableState<Int>,
    var children: MutableState<Int>
)
