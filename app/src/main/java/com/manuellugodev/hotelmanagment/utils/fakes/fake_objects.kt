package com.manuellugodev.hotelmanagment.utils.fakes

import com.manuellugodev.hotelmanagment.domain.model.Customer
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

val roomMock = RoomHotel(
    "test",
    2,
    "https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?cs=srgb&dl=pexels-pixabay-164595.jpg&fm=jpg",
    2,
    200.0
)
val customer= Customer("2312312","Manuel","Lugo")
val reservationMock=Reservation(
    customer, roomMock,1700799185000,1700971985000,120,12,132

)