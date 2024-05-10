package com.manuellugodev.hotelmanagment.features.core.domain.model


data class Reservation(
    var id: Int,
    val client: Customer,
    val roomHotel: RoomHotel,
    val checkIn: Long,
    val checkOut: Long,
    val price: Double,
    val taxPrice: Double,
    val totalPrice: Double
) {
    override fun toString(): String {
        return "Reservation(id=$id, client=$client, roomHotel=$roomHotel, checkIn=$checkIn, checkOut=$checkOut, price=$price, taxPrice=$taxPrice, totalPrice=$totalPrice)"
    }
}


