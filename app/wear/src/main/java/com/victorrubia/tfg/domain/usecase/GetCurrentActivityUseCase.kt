package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

class GetCurrentActivityUseCase(private val activityRepository : ActivityRepository) {

    suspend fun execute() : Activity? = activityRepository.getCurrentActivity()

}