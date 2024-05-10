package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.profile.data.DataSourceProfile
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.framework.network.request.ProfileRequest
import com.manuellugodev.hotelmanagment.utils.DataResult

class DataSourceProfileApi(private val request: ProfileRequest) : DataSourceProfile {
    override suspend fun getDataProfile(username: String): DataResult<Profile> {

        val result = request.service.getProfileData(username)

        return if (result.isSuccessful) {
            val profileApi = result.body()!!
            val profile = Profile(
                profileApi.username,
                "${profileApi.guestId.firstName} ${profileApi.guestId.lastName}",
                profileApi.guestId.email,
                profileApi.guestId.phone
            )
            DataResult.Success(profile)
        } else {
            DataResult.Error(Exception())
        }
    }
}