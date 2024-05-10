package com.manuellugodev.hotelmanagment.features.profile.usecase

import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

class DoLogOutSession(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(): Result<Unit> {

        return loginRepository.doLogOut()
    }
}