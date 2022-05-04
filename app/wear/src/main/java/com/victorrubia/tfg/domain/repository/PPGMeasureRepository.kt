package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

interface PPGMeasureRepository {

    suspend fun savePPGMeasure(ppgMeasure : PPGMeasure, activityId : Int)

    suspend fun endPPGMeasure(activityId : Int)
}