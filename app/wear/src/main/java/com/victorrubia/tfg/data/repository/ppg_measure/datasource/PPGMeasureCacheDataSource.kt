package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

interface PPGMeasureCacheDataSource {
    suspend fun getPPGMeasureFromCache(): List<PPGMeasure>
    suspend fun addPPGMeasureToCache(ppgMeasure: PPGMeasure)
    suspend fun clearAll()
}