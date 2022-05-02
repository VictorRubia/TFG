package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

@Database(entities = [Activity::class, PPGMeasure::class],
version = 1,
exportSchema = false
)
abstract class TFGDatabase : RoomDatabase(){

    abstract fun activityDao() : ActivityDao

    abstract fun ppgMeasureDao() : PPGMeasureDao
}