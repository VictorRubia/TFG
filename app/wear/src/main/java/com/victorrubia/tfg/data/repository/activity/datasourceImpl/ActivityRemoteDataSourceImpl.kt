package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import retrofit2.Response

class ActivityRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val apiKey : String
) : ActivityRemoteDataSource {

    override suspend fun newActivity(name: String,
                                     startTimestamp: String) : Response<Activity> = tfgService.newActivity(apiKey, name, startTimestamp)

    override suspend fun endActivity(activityId: Int, endTimestamp: String): Response<Activity> = tfgService.endActivity(apiKey, activityId, endTimestamp)
}