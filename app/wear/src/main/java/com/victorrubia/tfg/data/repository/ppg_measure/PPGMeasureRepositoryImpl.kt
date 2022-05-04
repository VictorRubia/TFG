package com.victorrubia.tfg.data.repository.ppg_measure

import android.util.Log
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PPGMeasureRepositoryImpl(
    private val ppgMeasureRemoteDataSource: PPGMeasureRemoteDataSource,
    private val ppgMeasureLocalDataSource: PPGMeasureLocalDataSource,
    private val ppgMeasureCacheDataSource: PPGMeasureCacheDataSource,
) : PPGMeasureRepository {

    private var count = 0

    override suspend fun savePPGMeasure(ppgMeasure: PPGMeasure, activityId: Int) {

        try {
            ppgMeasureCacheDataSource.addPPGMeasureToCache(ppgMeasure)
            count++
            if(count == 250){
                count = 0
                sendPPGMeasureToAPI(ppgMeasureCacheDataSource.getPPGMeasureFromCache(), activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

    }

    override suspend fun endPPGMeasure(activityId : Int) {
        try {
            sendPPGMeasureToAPI(ppgMeasureCacheDataSource.getPPGMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    suspend fun sendPPGMeasureToAPI(ppgMeasure : List<PPGMeasure>, activityId : Int){

        try{
            checkExistingMeasurements(activityId)
//            Log.d("MyTag", "Activity $activityId")
            val response = ppgMeasureRemoteDataSource.sendPPGMeasures(Json.encodeToString(ppgMeasure), activityId)
            val body = response.body()
            if(body != null){
//                Log.d("MyTag", "Enviado el PPG Measure")
                ppgMeasureLocalDataSource.clearAll()
                ppgMeasureCacheDataSource.clearAll()
            }
            else{
                savePPGMeasuresToDB(ppgMeasure)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", "SendPPGMeasureToAPI - " + exception.message.toString())
        }
    }

    suspend fun savePPGMeasuresToDB(ppgMeasures: List<PPGMeasure>){
        try {
            ppgMeasures.forEach{ppgMeasure -> ppgMeasureLocalDataSource.addPPGMeasureToDB(ppgMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    suspend fun checkExistingMeasurements(activityId: Int){
        try {
            val list = ppgMeasureLocalDataSource.getPPGMeasureFromDB()
            if(list.isNotEmpty()){
                sendPPGMeasureToAPI(list, activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

}