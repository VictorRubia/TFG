package com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource

class PPGMeasureCacheDataSourceImpl : PPGMeasureCacheDataSource {

    private var ppgMeasures : MutableList<PPGMeasure> = ArrayList()

    override suspend fun getPPGMeasureFromCache(): List<PPGMeasure> {
        return ppgMeasures
    }

    override suspend fun addPPGMeasureToCache(ppgMeasure: PPGMeasure) {
        ppgMeasures.add(ppgMeasure)
    }

    override suspend fun clearAll() {
        ppgMeasures.clear()
    }
}