package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

interface PPGMeasureLocalDataSource {
    suspend fun getPPGMeasureFromDB() : List<PPGMeasure>
    suspend fun addPPGMeasureToDB(ppgMeasures : PPGMeasure)
    suspend fun clearAll()
}