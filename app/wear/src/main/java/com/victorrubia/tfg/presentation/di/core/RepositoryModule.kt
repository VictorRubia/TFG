package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.activity.ActivityRepositoryImpl
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideActivityRepository(
        activityRemoteDataSource: ActivityRemoteDataSource,
        activityLocalDataSource: ActivityLocalDataSource,
        activityCacheDataSource: ActivityCacheDataSource
    ): ActivityRepository{
        return ActivityRepositoryImpl(
            activityRemoteDataSource,
            activityLocalDataSource,
            activityCacheDataSource
        )
    }
}