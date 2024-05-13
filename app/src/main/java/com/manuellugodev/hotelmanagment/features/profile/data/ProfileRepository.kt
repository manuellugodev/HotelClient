package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

interface ProfileRepository {

    suspend fun getDataProfile(): Result<Profile>
}