package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.domain.utils.DataResult

interface ProfileRepository {

    suspend fun getDataProfile(): DataResult<Profile>
}