package com.manuellugodev.hotelmanagment.features.profile.presentation

import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.TestDistpatchers
import com.manuellugodev.hotelmanagment.features.profile.usecase.DoLogOutSession
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.utils.fakeProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest{

    private val getDataProfile:GetDataProfile = mockk()
    private val doLogOutSession:DoLogOutSession = mockk()
    private val standarTest= StandardTestDispatcher()
    private lateinit var viewModel: ProfileViewModel


    @get:Rule
    val rule= MainCoroutineExtension(standarTest)

    @Before
    fun setUp(){
     viewModel= ProfileViewModel(getDataProfile,doLogOutSession,TestDistpatchers(standarTest))
    }
    @Test
    fun`WHEN LoadProfile event is called then called getDataProfileUsecase`()= runTest{

        viewModel.onEvent(ProfileEvent.LoadProfile)
        advanceUntilIdle()

        coVerify { getDataProfile.invoke() }
    }

    @Test
    fun`WHEN LoadProfile event is called and GetDataProfile is success then update state with Profile`()= runTest{

        val mocckProfile= fakeProfile()
        coEvery { getDataProfile.invoke() } returns Result.success(mocckProfile)

        viewModel.onEvent(ProfileEvent.LoadProfile)
        advanceUntilIdle()

        assert(viewModel.stateProfile.value.showProfile== mocckProfile)
    }
    @Test
    fun`WHEN LoadProfile event is called and GetDataProfile is failure then update state with Error`()= runTest{

            coEvery { getDataProfile.invoke() } returns Result.failure(Exception())

            viewModel.onEvent(ProfileEvent.LoadProfile)
            advanceUntilIdle()

            assert(viewModel.stateProfile.value.showError.isNotEmpty())
        }

    @Test
    fun`WHEN LogOutSession event is called then called LogOutUseCase`()= runTest{
        viewModel.onEvent(ProfileEvent.LogOutSession)
        advanceUntilIdle()

        coVerify { doLogOutSession.invoke() }

    }
    @Test
    fun`WHEN LogOutSession event is called and LogOutUseCase is success the update state logOut with true`()= runTest{
        coEvery { doLogOutSession.invoke() } returns Result.success(Unit)
        viewModel.onEvent(ProfileEvent.LogOutSession)
        advanceUntilIdle()

        assert(viewModel.stateProfile.value.isLogOut==true)
    }
    @Test
    fun`WHEN LogOutSession event is called and LogOutUseCase is failure the update state with error`()= runTest{
        coEvery { doLogOutSession.invoke() } returns Result.failure(Exception())
        viewModel.onEvent(ProfileEvent.LogOutSession)
        advanceUntilIdle()

        assert(viewModel.stateProfile.value.isLogOut==false)
        assert(viewModel.stateProfile.value.showError.isNotEmpty())
    }


}