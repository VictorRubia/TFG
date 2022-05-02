package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity
import retrofit2.Response


interface ActivityRemoteDataSource {

    suspend fun newActivity(name : String, startTimestamp : String) : Response<Activity>

    suspend fun endActivity(activityId : Int, endTimestamp: String) : Response<Activity>

}