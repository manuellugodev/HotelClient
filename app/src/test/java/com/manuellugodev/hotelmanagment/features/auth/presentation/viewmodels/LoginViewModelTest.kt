package com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels

import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.TestDistpatchers
import com.manuellugodev.hotelmanagment.features.auth.domain.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest{

    private lateinit var viewModel: LoginViewModel
    private val standardTest= StandardTestDispatcher()
    private val loginUseCase:LoginWithUsernameAndPassword= mockk(relaxed = true)

    @get:Rule
    val rule=MainCoroutineExtension(standardTest)
    @Before
    fun setUp(){
        viewModel= LoginViewModel(loginUseCase,TestDistpatchers(standardTest))
    }

    @Test
    fun`DoLogin Event is called then call to LoginUseCase`()= runTest {

        viewModel.onEvent(LoginEvent.DoLoginEvent)
        advanceUntilIdle()
        coVerify { loginUseCase.invoke(any(),any()) }
    }

    @Test
    fun`DoLogin Event is called and LoginUseCase is success THEN update state loginSucess`()= runTest {
        coEvery { loginUseCase.invoke(any(),any()) } returns Result.success(Unit)
        viewModel.onEvent(LoginEvent.DoLoginEvent)
        advanceUntilIdle()

        assertEquals(true,viewModel.statusLogin.value.loginSuccess)
        assert(viewModel.statusLogin.value.showError.isEmpty())

    }

    @Test
    fun`DoLogin Event is called and LoginUseCase is failure THEN update state showError`()= runTest {
        val exception=Exception()

        coEvery { loginUseCase.invoke(any(),any()) } returns Result.failure(exception)
        viewModel.onEvent(LoginEvent.DoLoginEvent)
        advanceUntilIdle()

        assert(viewModel.statusLogin.value.showError.isNotEmpty())

    }

    @Test
    fun`DismissError event is called then update state shoeError with empty value`()= runTest {
        val exception=Exception()

        coEvery { loginUseCase.invoke(any(),any()) } returns Result.failure(exception)
        viewModel.onEvent(LoginEvent.DoLoginEvent)

        advanceUntilIdle()
        assert(viewModel.statusLogin.value.showError.isNotEmpty())
        viewModel.onEvent(LoginEvent.DismissError)
        advanceUntilIdle()

        assert(viewModel.statusLogin.value.showError.isEmpty())

    }
    @Test
    fun`OnUsernameEnter Event is called THEN UPDATE state UsernameEnter with username`(){
        val username="Manuel"
        viewModel.onEvent(LoginEvent.OnUsernameEnter(username = username))

        assertEquals(username,viewModel.statusLogin.value.usernameEnter)
    }
    @Test
    fun`OnPasswordEnter Event is called THEN UPDATE state PasswordEnter with password`(){
        val password="password"
        viewModel.onEvent(LoginEvent.OnPasswordEnter(password = password))

        assertEquals(password,viewModel.statusLogin.value.passwordeEnter)
    }

    @Test
    fun`VisibilityPassword event is called with param set true THEN update state showPassword to true`(){
        val isVisible=true
        viewModel.onEvent(LoginEvent.VisibilityPassword(isVisible = isVisible))
        assertEquals(isVisible,viewModel.statusLogin.value.showPassword)
    }
    @Test
    fun`VisibilityPassword event is called with param set false THEN update state showPassword to false`(){
        val isVisible=false
        viewModel.onEvent(LoginEvent.VisibilityPassword(isVisible = isVisible))
        assertEquals(isVisible,viewModel.statusLogin.value.showPassword)
    }

}