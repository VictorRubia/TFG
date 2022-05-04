package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository

class SavePPGMeasureUseCase(private val ppgMeasureRespository: PPGMeasureRepository) {

    suspend fun execute(ppgMeasure : PPGMeasure, activityId : Int) =
        ppgMeasureRespository.savePPGMeasure(ppgMeasure, activityId)
}