package com.victorrubia.tfg

import data.Actividad
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ActividadAPI {
    @FormUrlEncoded
    @POST("activities")
    suspend fun addActivity(@Header("Authorization") authToken : String,
                            @Field("activity[name]") name: String,
                            @Field("activity[user_id]") user_id: String,
                            @Field("activity[start_d]") start_d : String,
    ) : Response<Actividad>

    @FormUrlEncoded
    @PUT("activities/{Id}")
    suspend fun updateActivity(@Header("Authorization") authToken : String,
                               @Field("activity[end_d]") end_d : String,
                               @Path("Id") activityID: String,
                               ) : Response<Actividad>
}