package com.victorrubia.tfg

import data.Actividad
import retrofit2.Response
import retrofit2.http.*

interface PPGMeasureAPI {
    @FormUrlEncoded
    @POST("ppg_measures")
    suspend fun addMeasure(@Header("Authorization") authToken : String,
                            @Field("ppg_measure[measurement]") measurement: String,
                            @Field("ppg_measure[activity_id]") activity_id: String,
    ) : Response<Actividad>
}