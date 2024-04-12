package com.manuellugodev.hotelmanagment.features.reservations.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver

data class NumberGuest(
    var adults: MutableState<Int>,
    var children: MutableState<Int>
)

fun NumberGuest.getSum(): Int {
    return this.adults.value+this.children.value
}

fun NumberGuest.getText():String{
    val buildString=StringBuilder()
    if(adults.value!=0){
        buildString.append("Adults : ${adults.value} ")
    }
    if(children.value!=0){
        buildString.append("Children: ${children.value}")
    }
    return buildString.toString()
}


val numberGuestSaver = Saver<NumberGuest, Map<String,Int>>(
    save = { mapOf(Pair("adults",it.adults.value),Pair("children",it.children.value)) },
    restore = {
        NumberGuest(
            mutableStateOf(it["adults"] ?: 0),
            (mutableStateOf(it["children"] ?: 0))
        )
    }
)


