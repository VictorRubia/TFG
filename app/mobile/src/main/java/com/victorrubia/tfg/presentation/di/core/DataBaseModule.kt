package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfg.data.db.TFGDatabase
import com.victorrubia.tfg.data.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideUserDataBase(context : Context) : TFGDatabase {
        return Room.databaseBuilder(context, TFGDatabase::class.java, "tfgclient")
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(tfgDatabase: TFGDatabase) : UserDao {
        return tfgDatabase.userDao()
    }
}