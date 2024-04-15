package com.manuellugodev.hotelmanagment.network.source

import com.manuellugodev.hotelmanagment.features.profile.Profile
import com.manuellugodev.hotelmanagment.features.profile.data.DataSourceProfile
import com.manuellugodev.hotelmanagment.network.request.ProfileRequest
import com.manuellugodev.hotelmanagment.utils.DataResult

class DataSourceProfileApi(request: ProfileRequest) : DataSourceProfile {
    override suspend fun getDataProfile(): DataResult<Profile> {
        return DataResult.Success(Profile("Mnuel"))
    }
}