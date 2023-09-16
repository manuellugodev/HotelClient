package com.manuellugodev.hotelmanagment.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver

data class NumberGuest(
    var adults: MutableState<Int>,
    var children: MutableState<Int>
)

val numberGuestSaver = Saver<NumberGuest, Map<String,Int>>(
    save = { mapOf(Pair("adults",it.adults.value),Pair("children",it.children.value)) },
    restore = {NumberGuest(mutableStateOf(it["adults"]?:0), (mutableStateOf(it["children"]?:0))) }
)


