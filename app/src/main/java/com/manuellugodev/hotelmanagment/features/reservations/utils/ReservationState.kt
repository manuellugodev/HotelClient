package com.manuellugodev.hotelmanagment.features.reservations.utils

data class ReservationState(
    val showDatePicker:Boolean=false,
    val showGuestSelector:Boolean=false,
    val showError:String="",
    val numberGuestAdults:Int=0,
    val numberGuestChildren: Int = 0,
    val navigateToSearchRooms:NavigateSearchParams?=null
    )

data class NavigateSearchParams(val startTime:Long, val endTime: Long, val guests: Long)
