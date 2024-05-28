package com.manuellugodev.hotelmanagment.features.profile.domain.usecase

import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepository
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile

class GetDataProfile(private val repository: ProfileRepository) {

    suspend operator fun invoke(): Result<Profile> {
        return repository.getDataProfile()
    }
}