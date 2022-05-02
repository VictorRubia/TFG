package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity

interface ActivityLocalDataSource {
    suspend fun getActivityFromDB() : Activity
    suspend fun saveActivityToDB(activity: Activity)
    suspend fun clearAll()
}