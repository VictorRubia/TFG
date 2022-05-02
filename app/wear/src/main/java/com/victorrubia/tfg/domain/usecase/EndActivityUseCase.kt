package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

class EndActivityUseCase(private val activityRepository : ActivityRepository) {

    suspend fun execute(activityId: Int, endTimestamp: String) : Activity? = activityRepository.endActivity(activityId, endTimestamp)

}