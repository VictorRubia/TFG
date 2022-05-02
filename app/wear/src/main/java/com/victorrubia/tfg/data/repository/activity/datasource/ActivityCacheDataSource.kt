package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity

interface ActivityCacheDataSource {
    suspend fun getActivityFromCache() : Activity?
    suspend fun saveActivityToCache(activity: Activity)
    suspend fun clearAll()
}