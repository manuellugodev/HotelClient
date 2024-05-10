package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.domain.TokenManagment
import com.manuellugodev.hotelmanagment.utils.DataResult

class ProfileRepositoryImpl(
    private val sourceProfile: DataSourceProfile,
    private val tokenManagment: TokenManagment
) : ProfileRepository {
    override suspend fun getDataProfile(): DataResult<Profile> {
        val username = "manuel"
        return sourceProfile.getDataProfile(username)
    }
}