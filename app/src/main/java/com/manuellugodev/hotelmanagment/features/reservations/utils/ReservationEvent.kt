package com.manuellugodev.hotelmanagment.features.reservations.utils

sealed class ReservationEvent {
    data class OnVisibleDatePicker(val visible:Boolean):ReservationEvent()

    data class OnVisibleGuestComposable(val visible: Boolean):ReservationEvent()

    data class ShowError(val message:String):ReservationEvent()

    object DismissError:ReservationEvent()

    data class NavigateToSearchRooms(val startTime: Long, val endTime: Long, val guests: Long) :ReservationEvent()

    data class UpdateAdultsCount(val num:Int):ReservationEvent()

    data class UpdateChildrenCount(val num:Int):ReservationEvent()
    object CleanNavigation : ReservationEvent()

}
