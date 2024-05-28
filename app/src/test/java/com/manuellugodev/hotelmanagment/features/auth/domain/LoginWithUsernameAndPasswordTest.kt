package com.manuellugodev.hotelmanagment.features.auth.domain

import com.manuellugodev.hotelmanagment.features.auth.data.FakeLoginRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginWithUsernameAndPasswordTest{

    private lateinit var loginWithUsernameAndPassword:LoginWithUsernameAndPassword
    private lateinit var repository:FakeLoginRepository

    @Before
    fun setUp(){
        repository=FakeLoginRepository()
        loginWithUsernameAndPassword= LoginWithUsernameAndPassword(repository)
    }

    @Test
    fun`WHEN loginWithUsernameAndPasswordRepository is success THEN Usecase return success`()= runTest{
        val email="prueba@gmail.com"
        val password="password"
        val result = loginWithUsernameAndPassword(email,password)
        assertEquals(Result.success(Unit),result)
    }

    @Test
    fun`WHEN loginWithUsernameAndPasswordRepository is failure THEN Usecase return failure`()= runTest{
        val email="prueba@gmail.com"
        val password="password"
        repository.shouldReturnError=true
        val errorMessage="Error Login"
        repository.errorMessage=errorMessage
        val result = loginWithUsernameAndPassword(email,password)
        assert(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message,errorMessage)
    }

}