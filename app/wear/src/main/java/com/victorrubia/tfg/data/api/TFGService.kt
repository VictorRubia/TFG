package com.victorrubia.tfg.data.api

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import retrofit2.Response
import retrofit2.http.*

interface TFGService {

    @POST("activities/")
    suspend fun newActivity(@Header("Authorization") apiKey : String,
                            @Query("activity[name]") name : String,
                            @Query("activity[start_d]") startTimestamp : String ) : Response<Activity>

    @PUT("activities/{id}")
    suspend fun endActivity(@Header("Authorization") apiKey : String,
                            @Path(value = "id") activityId : Int,
                            @Query("activity[end_d]") endTimestamp : String) : Response<Activity>

    @FormUrlEncoded
    @POST("ppg_measures/")
    suspend fun addPPGMeasure(@Header("Authorization") apiKey : String,
                              @Field("ppg_measure[measurement]") measurement: String,
                              @Field("ppg_measure[activity_id]") activityID: Int,
    ) : Response<*>
}