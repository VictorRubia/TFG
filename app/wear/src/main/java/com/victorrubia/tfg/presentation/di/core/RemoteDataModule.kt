package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey : String) {

    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfgService: TFGService) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfgService, apiKey)
    }
}