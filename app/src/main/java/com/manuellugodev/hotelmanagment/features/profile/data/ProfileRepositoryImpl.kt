package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.Profile
import com.manuellugodev.hotelmanagment.network.TokenManagment
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