package com.victorrubia.tfg.data.api

import com.victorrubia.tfg.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TFGService {

    @GET("users/get_api_key/")
    suspend fun getUserInfo(@Query("email") email : String,
                            @Query("password_digest") password : String ) : Response<User>

    @GET("users/password_recovery/")
    suspend fun requestPasswordRecovery(@Query("email") email:String ) : Response<*>

}