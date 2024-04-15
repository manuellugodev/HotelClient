package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.Profile
import com.manuellugodev.hotelmanagment.utils.DataResult

class ProfileRepositoryImpl(private val sourceProfile: DataSourceProfile) : ProfileRepository {
    override suspend fun getDataProfile(): DataResult<Profile> {
        return sourceProfile.getDataProfile()
    }
}