package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.db.ActivityDao
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityLocalDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideActivityLocalDataSource(activityDao: ActivityDao) : ActivityLocalDataSource{
        return ActivityLocalDataSourceImpl(activityDao)
    }

    @Singleton
    @Provides
    fun provideUserLocalDataSource(userDao: UserDao) : UserLocalDataSource {
        return UserLocalDataSourceImpl(userDao)
    }


}