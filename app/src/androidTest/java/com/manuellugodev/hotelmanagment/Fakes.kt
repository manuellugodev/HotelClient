package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.features.core.domain.model.Customer
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile

fun fakeListHotel():List<RoomHotel>{
    val hotelRooms = listOf(
        RoomHotel(
            id = 1,
            description = "Cozy single room with a beautiful city view",
            roomType = "Single",
            pathImage = "path/to/image1.jpg",
            peopleQuantity = 1,
            price = 100.0
        ),
        RoomHotel(
            id = 2,
            description = "Spacious double room ideal for couples",
            roomType = "Double",
            pathImage = "path/to/image2.jpg",
            peopleQuantity = 2,
            price = 180.0
        ),
        RoomHotel(
            id = 3,
            description = "Luxurious suite with modern amenities",
            roomType = "Suite",
            pathImage = "path/to/image3.jpg",
            peopleQuantity = 4,
            price = 300.0
        )
    )

    return hotelRooms
}

fun fakeReservation():Reservation{
    return Reservation(1, fakeCustomer(), fakeRoomHotel(),1000,1000,200.0,10.0,10.0)
}

fun fakeCustomer():Customer{
    return Customer(1L,"Customer1","lastname","cla@gmail.com","123456789")
}

fun fakeRoomHotel():RoomHotel{
    return RoomHotel(
        id = 1,
        description = "Cozy single room with a beautiful city view",
        roomType = "Single",
        pathImage = "path/to/image1.jpg",
        peopleQuantity = 1,
        price = 100.0
    )
}

fun fakeProfile():Profile{
    return Profile("Manuelg20","Manuel","Lugo","example@gmail.com","12345678")
}