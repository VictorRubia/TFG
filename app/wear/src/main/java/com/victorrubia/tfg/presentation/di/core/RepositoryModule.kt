package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.activity.ActivityRepositoryImpl
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.PPGMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.tag.TagRepositoryImpl
import com.victorrubia.tfg.data.repository.tag.datasource.TagCacheDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityRepository
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository
import com.victorrubia.tfg.domain.repository.TagRepository
import com.victorrubia.tfg.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides repository dependencies.
 */
@Module
class RepositoryModule {

    /**
     * Provides the [ActivityRepository] implementation.
     *
     * @param activityCacheDataSource the [ActivityCacheDataSource] implementation.
     * @param activityLocalDataSource the [ActivityLocalDataSource] implementation.
     * @param activityRemoteDataSource the [ActivityRemoteDataSource] implementation.
     * @return the [ActivityRepository] implementation.
     */
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

    /**
     * Provides the [UserRepository] implementation.
     *
     * @param userRemoteDataSource the [UserRemoteDataSource] implementation.
     * @param userLocalDataSource the [UserLocalDataSource] implementation.
     * @param userCacheDataSource the [UserCacheDataSource] implementation.
     * @return the [UserRepository] implementation.
     */
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

    /**
     * Provides the [PPGMeasureRepository] implementation.
     *
     * @param ppgMeasureCacheDataSource the [PPGMeasureCacheDataSource] implementation.
     * @param ppgMeasureLocalDataSource the [PPGMeasureLocalDataSource] implementation.
     * @param ppgMeasureRemoteDataSource the [PPGMeasureRemoteDataSource] implementation.
     * @return the [PPGMeasureRepository] implementation.
     */
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

    /**
     * Provides the [TagRepository] implementation.
     *
     * @param tagCacheDataSource the [TagCacheDataSource] implementation.
     * @param tagLocalDataSource the [TagLocalDataSource] implementation.
     * @param tagRemoteDataSource the [TagRemoteDataSource] implementation.
     * @return the [TagRepository] implementation.
     */
    @Singleton
    @Provides
    fun provideTagRepository(
         tagRemoteDataSource : TagRemoteDataSource,
         tagLocalDataSource: TagLocalDataSource,
         tagCacheDataSource: TagCacheDataSource
    ): TagRepository {
        return TagRepositoryImpl(
            tagRemoteDataSource,
            tagLocalDataSource,
            tagCacheDataSource,
        )
    }
}