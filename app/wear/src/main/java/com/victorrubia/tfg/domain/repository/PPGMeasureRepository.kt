package com.victorrubia.tfg.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

interface PPGMeasureRepository {

    suspend fun savePPGMeasure(ppgMeasure : PPGMeasure, activityId : Int) : MutableState<Boolean>

    suspend fun endPPGMeasure(activityId : Int)
}