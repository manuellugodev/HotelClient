package com.manuellugodev.hotelmanagment.features.auth.domain


import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository

class LoginWithUsernameAndPassword(private val repository: LoginRepository) {

    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        // Business validation in Use Case
        if (username.isBlank()) {
            return Result.failure(ValidationException("Username cannot be empty"))
        }

        if (password.isBlank()) {
            return Result.failure(ValidationException("Password cannot be empty"))
        }

        val trimmedUsername = username.trim()

        // Additional business validation rules if needed
        if (trimmedUsername.length < 2) {
            return Result.failure(ValidationException("Username must be at least 2 characters"))
        }

        // Proceed with actual login
        return repository.doLogin(trimmedUsername, password)
    }
}

// Custom exception for validation errors
class ValidationException(message: String) : Exception(message)