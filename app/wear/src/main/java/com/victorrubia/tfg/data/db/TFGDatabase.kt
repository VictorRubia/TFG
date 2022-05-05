package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.model.user.User

@Database(entities = [Activity::class, PPGMeasure::class, User::class, Tag::class],
version = 1,
exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFGDatabase : RoomDatabase(){

    abstract fun activityDao() : ActivityDao

    abstract fun ppgMeasureDao() : PPGMeasureDao

    abstract fun userDao() : UserDao

    abstract fun tagDao() : TagDao
}