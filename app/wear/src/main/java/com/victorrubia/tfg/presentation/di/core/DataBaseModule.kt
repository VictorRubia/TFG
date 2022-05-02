package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfg.data.db.ActivityDao
import com.victorrubia.tfg.data.db.TFGDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideActivityDataBase(context : Context) : TFGDatabase{
        return Room.databaseBuilder(context, TFGDatabase::class.java, "tfgwear")
            .build()
    }

    @Singleton
    @Provides
    fun provideActivityDao(tfgDatabase: TFGDatabase) : ActivityDao{
        return tfgDatabase.activityDao()
    }
}