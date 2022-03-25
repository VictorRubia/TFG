package com.victorrubia.tfg

import data.Usuario
import retrofit2.Response
import retrofit2.http.*

interface UsuarioAPI {
    @GET("users")
    suspend fun getUsers(@Header("Authorization") authToken : String) : Response<Usuario>

    @GET("users/password_recovery")
    suspend fun recoverPassword(@Query("email") email: String)
}