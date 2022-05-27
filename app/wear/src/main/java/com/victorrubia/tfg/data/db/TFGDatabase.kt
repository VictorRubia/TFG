package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.model.user.User

/**
 * Room database for TFG application.
 */
@Database(entities = [Activity::class, PPGMeasure::class, User::class, Tag::class],
version = 1,
exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFGDatabase : RoomDatabase(){
    /**
     * DAO for [Activity] table.
     *
     * @return [ActivityDao]
     */
    abstract fun activityDao() : ActivityDao

    /**
     * DAO for [PPGMeasure] table.
     *
     * @return [PPGMeasureDao]
     */
    abstract fun ppgMeasureDao() : PPGMeasureDao

    /**
     * DAO for [User] table.
     *
     * @return [UserDao]
     */
    abstract fun userDao() : UserDao

    /**
     * DAO for [Tag] table.
     *
     * @return [TagDao]
     */
    abstract fun tagDao() : TagDao
}