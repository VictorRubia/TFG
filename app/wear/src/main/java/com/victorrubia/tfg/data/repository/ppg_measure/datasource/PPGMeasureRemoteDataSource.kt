package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import retrofit2.Response

interface PPGMeasureRemoteDataSource {

    suspend fun sendPPGMeasures(ppgMeasures: String, activityId : Int) : Response<*>

}