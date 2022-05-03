package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(tfgService : TFGService, context: Context) : UserRemoteDatasource {
        return UserRemoteDataSourceImpl(tfgService, context)
    }

}