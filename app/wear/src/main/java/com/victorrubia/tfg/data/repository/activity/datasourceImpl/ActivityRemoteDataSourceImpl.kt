package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import retrofit2.Response

class ActivityRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : ActivityRemoteDataSource {

    override suspend fun newActivity(name: String,
                                     startTimestamp: String) : Response<Activity> =
        tfgService.newActivity("Bearer ${user.getUserFromCache()!!.apiKey}", name, startTimestamp)


    override suspend fun endActivity(activityId: Int, endTimestamp: String): Response<Activity> =
        tfgService.endActivity("Bearer ${user.getUserFromCache()!!.apiKey}", activityId, endTimestamp)
}