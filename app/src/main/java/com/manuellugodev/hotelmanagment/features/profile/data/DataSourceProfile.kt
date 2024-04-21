package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.utils.DataResult

interface DataSourceProfile {

    suspend fun getDataProfile(username: String): DataResult<Profile>
}