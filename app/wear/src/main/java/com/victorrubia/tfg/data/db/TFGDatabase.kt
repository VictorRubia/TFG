package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.model.user.User

@Database(entities = [Activity::class, PPGMeasure::class, User::class],
version = 1,
exportSchema = false
)
abstract class TFGDatabase : RoomDatabase(){

    abstract fun activityDao() : ActivityDao

    abstract fun ppgMeasureDao() : PPGMeasureDao

    abstract fun userDao() : UserDao
}