package com.manuellugodev.hotelmanagment.features.profile.domain.usecase

import com.manuellugodev.hotelmanagment.features.profile.data.FakeProfileRepository
import com.manuellugodev.hotelmanagment.utils.fakeProfile
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetDataProfileTest {

    private lateinit var getDataProfile: GetDataProfile
    private lateinit var repository: FakeProfileRepository
    @Before
    fun setUp() {
        repository= FakeProfileRepository()
        getDataProfile= GetDataProfile(repository = repository)
    }

    @Test
    fun`WHEN GetDataProfile is success THEN GetDataProfile return success`()= runTest {
        val profile= fakeProfile()
        repository.profileToReturn= profile
        val result = getDataProfile.invoke()

        assertEquals(Result.success(profile),result)
    }

    @Test
    fun`WHEN GetDataProfile is failure THEN GetDataProfile return error`()= runTest {
        val messageError= "Error to get data Profile"
        repository.shouldReturnError=true
        repository.messageError=messageError
        val result = getDataProfile.invoke()

        assert(result.isFailure)
        assertEquals(messageError,result.exceptionOrNull()?.message)
    }
}