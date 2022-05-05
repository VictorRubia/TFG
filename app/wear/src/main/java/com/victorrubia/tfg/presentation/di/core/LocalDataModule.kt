package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.db.ActivityDao
import com.victorrubia.tfg.data.db.PPGMeasureDao
import com.victorrubia.tfg.data.db.TagDao
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityLocalDataSourceImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl.PPGMeasureLocalDataSourceImpl
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfg.data.repository.tag.datasourceImpl.TagLocalDataSourceImpl
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

    @Singleton
    @Provides
    fun providePPGMeasureLocalDataSource(ppgMeasureDao: PPGMeasureDao) : PPGMeasureLocalDataSource {
        return PPGMeasureLocalDataSourceImpl(ppgMeasureDao)
    }

    @Singleton
    @Provides
    fun provideTagLocalDataSource(tagDao: TagDao) : TagLocalDataSource {
        return TagLocalDataSourceImpl(tagDao)
    }


}