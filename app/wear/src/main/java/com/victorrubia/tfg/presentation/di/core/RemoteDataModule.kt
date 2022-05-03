package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
//class RemoteDataModule(private val apiKey : MutableLiveData<String>) {
class RemoteDataModule(private val apiKey : UserLocalDataSource) {

    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfgService: TFGService) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfgService, apiKey)
    }

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(context: Context) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(context)
    }
}