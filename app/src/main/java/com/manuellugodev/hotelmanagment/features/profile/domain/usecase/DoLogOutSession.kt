package com.manuellugodev.hotelmanagment.features.profile.domain.usecase

import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository

class DoLogOutSession(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(): Result<Unit> {

        return loginRepository.doLogOut()
    }
}