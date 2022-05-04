package com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

class PPGMeasureRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : PPGMeasureRemoteDataSource {

    override suspend fun sendPPGMeasures(ppgMeasures: String, activityId : Int) =
        tfgService.addPPGMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", ppgMeasures, activityId)
}