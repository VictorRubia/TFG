package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource

class ActivityCacheDataSourceImpl : ActivityCacheDataSource {

    private var activity : Activity? = null

    override suspend fun getActivityFromCache(): Activity? {
        return activity
    }

    override suspend fun saveActivityToCache(activity: Activity) {
        this.activity = activity
    }

    override suspend fun clearAll() {
        this.activity = null
    }

}