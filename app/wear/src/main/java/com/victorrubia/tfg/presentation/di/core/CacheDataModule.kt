package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityCacheDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Singleton
    @Provides
    fun provideActivityCacheDataSource() : ActivityCacheDataSource{
        return ActivityCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideUserCacheDataSource() : UserCacheDataSource {
        return UserCacheDataSourceImpl()
    }
}