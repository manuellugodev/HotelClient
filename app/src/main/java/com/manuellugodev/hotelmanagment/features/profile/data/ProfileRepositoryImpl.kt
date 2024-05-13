package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

class ProfileRepositoryImpl(
    private val sourceProfile: DataSourceProfile,
    private val tokenManagment: TokenManagment
) : ProfileRepository {
    override suspend fun getDataProfile(): Result<Profile> {
        val username = "manuel"
        return sourceProfile.getDataProfile(username)
    }
}