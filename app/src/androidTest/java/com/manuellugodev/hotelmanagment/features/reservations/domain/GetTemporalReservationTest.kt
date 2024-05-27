package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.HotelManagmentTest
import com.manuellugodev.hotelmanagment.fakeReservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepositoryImpl
import com.manuellugodev.hotelmanagment.framework.network.source.FakeSourceAppointment
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationRoomDB
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationLocal
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@HiltAndroidTest
class GetTemporalReservationTest:HotelManagmentTest(){

    private lateinit var getTemporalReservation: GetTemporalReservation
    private lateinit var repository: ReservationRepositoryImpl
    private lateinit var localSource: DataSourceReservationRoomDB
    private lateinit var remoteSource: FakeSourceAppointment

    override fun setUp() {
        super.setUp()
        localSource= DataSourceReservationRoomDB(db.reservationDao())
        remoteSource= FakeSourceAppointment()
        repository= ReservationRepositoryImpl(remoteSource,localSource)
        getTemporalReservation= GetTemporalReservation(repository)
    }

    @Test
    fun verify_when_is_called_usecase_reservation_is_retrieved()= runTest {
        val reservation= fakeReservation()
        db.reservationDao().saveTemporalReservation(reservation =reservation.toReservationLocal() )

        val result = getTemporalReservation(reservation.id.toLong())

        when(result){
            is DataResult.Error -> throw Exception("Some is wrong getting Reservation with id ${reservation.id}")
            is DataResult.Success -> assertEquals(reservation,result.data)
        }

    }
}