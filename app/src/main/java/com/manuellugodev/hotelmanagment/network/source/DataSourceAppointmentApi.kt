package com.manuellugodev.hotelmanagment.network.source

import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.domain.model.Customer
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.network.AppointmentRequest
import com.manuellugodev.hotelmanagment.network.entities.Appointment
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import kotlin.random.Random

class DataSourceAppointmentApi(private val request: AppointmentRequest) : DataSourceReservation {
    override suspend fun saveReservation(reservation: Reservation): DataResult<Reservation> {
        TODO("Not yet implemented")
    }

    override suspend fun getReservation(): DataResult<List<Reservation>> {
        try {


            val result = request.service.getAppointments()

            if (result.isSuccessful) {
                val reservations = result.body()

                if(!reservations.isNullOrEmpty()){
                    return DataResult.Success(reservations.map { it.toReservation() })
                }

            } else {

                DataResult.Error(Exception("error"))

            }
        } catch (e: Exception) {

            DataResult.Error(Exception("error"))
        }

        return DataResult.Error(Exception("error"))

    }
}


fun Appointment.toReservation(): Reservation {

    return  generateRandomReservation()
}

fun generateRandomReservation(): Reservation {
    val random = Random.Default
    val clientId = random.nextInt(1000).toString() // Generating a random client ID
    val client = Customer(id = "1", firstName = "John", lastName = "Doe", email = "johndoe@example.com", phone = "+1234567890") // Replace with actual random data

    // Generating random room data
    val roomId = random.nextLong(1000)
    val roomTitle = "Room ${random.nextInt(100)}"
    val numberOfBeds = random.nextInt(1, 4) // Assuming rooms can have 1 to 4 beds
    val pathImage = "https://example.com/room_images/${random.nextInt(10)}.jpg"
    val peopleQuantity = numberOfBeds // Assuming each bed can accommodate one person
    val price = random.nextDouble(50.0, 300.0) // Random price between 50 and 300 dollars

    val room = RoomHotel(roomId, roomTitle, "Suite", pathImage, peopleQuantity, price)

    val checkIn = System.currentTimeMillis() + random.nextInt(1000) * 86400000L // Adding random days (in milliseconds) to current time
    val checkOut = checkIn + random.nextInt(10) * 86400000L // Adding random days (in milliseconds) to check-in date
    val price2 = random.nextInt(200, 500) // Random price between 200 and 500
    val taxPrice = (price * 0.1).toInt() // Assuming tax is 10% of the price
    val totalPrice = price + taxPrice // Total price including tax

    return Reservation(clientId, client, room, checkIn, checkOut, price.toInt(), taxPrice, price2)
}