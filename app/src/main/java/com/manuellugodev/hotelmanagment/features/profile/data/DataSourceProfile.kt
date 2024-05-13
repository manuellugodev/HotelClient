package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

interface DataSourceProfile {

    suspend fun getDataProfile(username: String): Result<Profile>
}