package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.HotelManagmentTest
import com.manuellugodev.hotelmanagment.fakeReservation
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepositoryImpl
import com.manuellugodev.hotelmanagment.framework.network.source.FakeSourceAppointment
import com.manuellugodev.hotelmanagment.framework.network.source.TokenManagmentFake
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationRoomDB
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationDomain
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@HiltAndroidTest
class SaveTemporalReservationTest: HotelManagmentTest() {

    private lateinit var useCase:SaveTemporalReservation
    private lateinit var repository: ReservationRepositoryImpl
    private lateinit var localSource:DataSourceReservationRoomDB
    private lateinit var remoteSource:FakeSourceAppointment
    private lateinit var tokenManagment: TokenManagment
    override fun setUp() {
        super.setUp()

        remoteSource= FakeSourceAppointment()
        localSource= DataSourceReservationRoomDB(db.reservationDao())
        tokenManagment = TokenManagmentFake()
        repository = ReservationRepositoryImpl(remoteSource, localSource, tokenManagment)
        useCase= SaveTemporalReservation(repository)

    }

    @Test
    fun `verify_when_is_called_usecase_reservation_is_saved`()=runTest{
        val reservation= fakeReservation()
        useCase.invoke(reservation)

        val result =db.reservationDao().findById(reservation.id.toLong()).toReservationDomain()
        assertEquals(reservation,result)
    }


}