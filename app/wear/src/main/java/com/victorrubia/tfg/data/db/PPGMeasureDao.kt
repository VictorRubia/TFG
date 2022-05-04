package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

@Dao
interface PPGMeasureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePPGMeasure(ppgMeasures: PPGMeasure)

    @Query("DELETE FROM ppg_measures")
    suspend fun deletePPGMeasures()

    @Query("SELECT * FROM ppg_measures")
    suspend fun getPPGMeasures() : List<PPGMeasure>
}