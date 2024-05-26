package com.manuellugodev.hotelmanagment.features.profile.data

import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.utils.fakeProfile

class FakeProfileRepository:ProfileRepository {

    var shouldReturnError=false
    var messageError="Error Get Profile"
    var profileToReturn= fakeProfile()
    override suspend fun getDataProfile(): Result<Profile> {
        return if(shouldReturnError){
            Result.failure(Exception(messageError))
        }else{
            Result.success(profileToReturn)
        }
    }
}