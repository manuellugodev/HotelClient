package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.profile.data.DataSourceProfile
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.framework.network.request.ProfileRequest

class DataSourceProfileApi(private val request: ProfileRequest) : DataSourceProfile {
    override suspend fun getDataProfile(username: String): Result<Profile> {

        val result = request.service.getProfileData(username)

        return if (result.isSuccessful) {
            val profileApi = result.body()?.data!!
            val profile = Profile(
               username =  profileApi.username,
               firstName =  profileApi.guestId.firstName,
               lastName =  profileApi.guestId.lastName,
               email =  profileApi.guestId.email,
               phone =  profileApi.guestId.phone
            )
            Result.success(profile)
        } else {
            Result.failure(Exception())
        }
    }
}