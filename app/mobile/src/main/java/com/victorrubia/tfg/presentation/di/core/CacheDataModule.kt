package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Singleton
    @Provides
    fun provideUserCacheDataSource() : UserCacheDataSource {
        return UserCacheDataSourceImpl()
    }
}