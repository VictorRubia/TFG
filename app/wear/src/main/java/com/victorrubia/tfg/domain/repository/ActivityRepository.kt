package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.activity.Activity

interface ActivityRepository {

    suspend fun newActivity(name : String, startTimestamp : String) : Activity

    suspend fun getCurrentActivity() : Activity?

    suspend fun endActivity() : Activity?

}