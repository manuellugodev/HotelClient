package com.manuellugodev.hotelmanagment.features.profile.usecase

import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepository
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

class GetDataProfile(private val repository: ProfileRepository) {

    suspend operator fun invoke(): Result<Profile> {
        return repository.getDataProfile()
    }
}