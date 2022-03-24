package com.victorrubia.tfg

import data.Usuario
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UsersAPI {
    @GET("users")
    suspend fun getUsers(@Header("Authorization") authToken : String) : Response<Usuario>
}