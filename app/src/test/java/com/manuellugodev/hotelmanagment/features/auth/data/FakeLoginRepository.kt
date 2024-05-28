package com.manuellugodev.hotelmanagment.features.auth.data

class FakeLoginRepository:LoginRepository {

    var shouldReturnError: Boolean = false
    var errorMessage: String = "failed"
    override suspend fun doLogin(email: String, password: String): Result<Unit> {
        return if (shouldReturnError){
            Result.failure<Unit>(Exception(errorMessage))
        }else{
            Result.success(Unit)
        }
    }

    override suspend fun doLogOut(): Result<Unit> {
        return if (shouldReturnError){
            Result.failure<Unit>(Exception(errorMessage))
        }else{
            Result.success(Unit)
        }
    }

    override suspend fun checkUserIsLogged(): Result<Unit> {
        return if (shouldReturnError){
            Result.failure(Exception(errorMessage))
        }else{
            Result.success(Unit)
        }
    }
}