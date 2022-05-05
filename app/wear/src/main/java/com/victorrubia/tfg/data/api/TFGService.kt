package com.victorrubia.tfg.data.api

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.tag.Tag
import retrofit2.Response
import retrofit2.http.*
import java.util.Date

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

    @FormUrlEncoded
    @POST("tags/")
    suspend fun addTag(@Header("Authorization") apiKey : String,
                              @Field("tag[tag]") tag: String,
                              @Field("tag[datetime]") dateTime: Date,
                              @Field("tag[activity_id]") activityID: Int,
    ) : Response<Tag>
}