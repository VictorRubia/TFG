package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

class NewActivityUseCase(private val activityRepository : ActivityRepository) {

    suspend fun execute(name: String, startTimestamp: String) : Activity? = activityRepository.newActivity(name, startTimestamp)

}