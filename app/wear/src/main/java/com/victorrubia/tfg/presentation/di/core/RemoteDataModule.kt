package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl.PPGMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
//class RemoteDataModule(private val apiKey : UserLocalDataSource) {
class RemoteDataModule() {

    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(context: Context) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(context)
    }

    @Singleton
    @Provides
    fun providePPGMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : PPGMeasureRemoteDataSource {
        return PPGMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }
}