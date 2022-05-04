package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.PPGMeasureRepository

class EndPPGMeasureUseCase(private val ppgMeasureRespository: PPGMeasureRepository) {

    suspend fun execute(activityId : Int) = ppgMeasureRespository.endPPGMeasure(activityId)
}