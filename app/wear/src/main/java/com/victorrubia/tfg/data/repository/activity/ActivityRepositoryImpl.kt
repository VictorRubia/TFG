package com.victorrubia.tfg.data.repository.activity

import android.util.Log
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityRepository
import java.time.Instant
import java.time.ZoneId
import java.util.*

class ActivityRepositoryImpl(
    private val activityRemoteDataSource : ActivityRemoteDataSource,
    private val activityLocalDataSource: ActivityLocalDataSource,
    private val activityCacheDataSource: ActivityCacheDataSource
) : ActivityRepository {

    override suspend fun newActivity(name: String, startTimestamp: String): Activity {
        return newActivityAPI(name, startTimestamp)
    }

    override suspend fun getCurrentActivity(): Activity? {
        return getActivityFromCache()
    }

    override suspend fun endActivity(): Activity? {
        return endActivityAPI()
    }

    suspend fun newActivityAPI(name: String, startTimestamp: String) : Activity {
        lateinit var activity : Activity

        try{
            val response = activityRemoteDataSource.newActivity(name, startTimestamp)
            val body = response.body()
            Log.d("MyTag", body.toString())
            if(body != null){
                activity = body
                activityLocalDataSource.saveActivityToDB(activity)
                activityCacheDataSource.saveActivityToCache(activity)
            }
        }
        catch (exception : Exception){
            Log.e("MyTagg", exception.message.toString())
        }

        return activity
    }

    suspend fun endActivityAPI() : Activity? {
        val activity: Activity? = activityCacheDataSource.getActivityFromCache()

        try {
            val response = activity?.id?.let { activityRemoteDataSource.endActivity(it, Instant.now().atZone(ZoneId.of("Europe/Madrid")).toString()) }
            val body = response?.body()
            if(body != null){
                activityLocalDataSource.clearAll()
                activityCacheDataSource.clearAll()
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return activity
    }

    suspend fun getActivityFromDB() : Activity?{
        var activity : Activity? = null

        try{
            activity = activityLocalDataSource.getActivityFromDB()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return activity
    }

    suspend fun getActivityFromCache() : Activity?{
        var activity : Activity? = null

        try{
            activity = activityCacheDataSource.getActivityFromCache()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        if(activity != null){
            return activity
        }
        else{
            activity = getActivityFromDB()
            if(activity != null) activityCacheDataSource.saveActivityToCache(activity)
        }

        return activity
    }
}