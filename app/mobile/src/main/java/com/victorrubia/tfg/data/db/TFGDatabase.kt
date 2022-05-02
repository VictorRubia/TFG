package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.user.User

@Database(entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFGDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

}