package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.Profile
import com.manuellugodev.hotelmanagment.utils.DataResult

interface ProfileRepository {

    suspend fun getDataProfile(): DataResult<Profile>
}