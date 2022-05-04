package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User
import retrofit2.Response

interface UserRemoteDatasource {

    suspend fun getUser(email : String, password : String): Response<User>

    suspend fun rememberPassword(email : String): Response<*>

    suspend fun sendApiKeyToWear(apiKey : String)

    suspend fun isWearConnected() : Boolean?

}