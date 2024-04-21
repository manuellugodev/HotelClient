package com.manuellugodev.hotelmanagment.features.profile.usecase

import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepository
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.utils.DataResult

class GetDataProfile(private val repository: ProfileRepository) {

    suspend operator fun invoke(): DataResult<Profile> {
        return repository.getDataProfile()
    }
}