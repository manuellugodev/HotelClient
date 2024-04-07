package com.manuellugodev.hotelmanagment.network.entities

import com.manuellugodev.hotelmanagment.domain.model.Customer

data class GuestApi(
    var guestId: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = ""
)

fun GuestApi.toCustomer(): Customer {
    return Customer(guestId.toLong(), firstName, lastName, email, phone)
}
