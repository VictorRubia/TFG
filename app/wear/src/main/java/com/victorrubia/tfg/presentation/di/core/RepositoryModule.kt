package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.data.repository.activity.ActivityRepositoryImpl
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.PPGMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityRepository
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository
import com.victorrubia.tfg.domain.repository.UserRepository
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
        activityCacheDataSource: ActivityCacheDataSource,
    ): ActivityRepository{
        return ActivityRepositoryImpl(
            activityRemoteDataSource,
            activityLocalDataSource,
            activityCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
        userCacheDataSource: UserCacheDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource,
            userLocalDataSource,
            userCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun providePPGMeasureRepository(
        ppgMeasureRemoteDataSource: PPGMeasureRemoteDataSource,
        ppgMeasureLocalDataSource: PPGMeasureLocalDataSource,
        ppgMeasureCacheDataSource: PPGMeasureCacheDataSource,
    ): PPGMeasureRepository {
        return PPGMeasureRepositoryImpl(
            ppgMeasureRemoteDataSource,
            ppgMeasureLocalDataSource,
            ppgMeasureCacheDataSource,
        )
    }
}