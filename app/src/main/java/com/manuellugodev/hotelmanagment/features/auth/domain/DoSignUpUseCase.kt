package com.manuellugodev.hotelmanagment.features.auth.domain

import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository

class DoSignUpUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(userRegisterModel: UserRegisterModel): Result<Unit> {

        return loginRepository.registerNewUser(userRegisterModel)

    }
}