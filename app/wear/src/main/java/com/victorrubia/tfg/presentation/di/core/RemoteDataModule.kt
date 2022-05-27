package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl.PPGMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.data.repository.tag.datasourceImpl.TagRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides remote data sources dependencies.
 */
@Module
class RemoteDataModule {

    /**
     * Provides activity remote data source.
     *
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [ActivityRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfgService, userDataSource)
    }

    /**
     * Provides user remote data source.
     *
     * @param context the context
     * @return [UserRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(context: Context) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(context)
    }

    /**
     * Provides PPGMeasure remote data source.
     *
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [PPGMeasureRemoteDataSource]
     */
    @Singleton
    @Provides
    fun providePPGMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : PPGMeasureRemoteDataSource {
        return PPGMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    /**
     * Provides tag remote data source.
     *
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [TagRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideTagRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : TagRemoteDataSource {
        return TagRemoteDataSourceImpl(tfgService, userDataSource)
    }
}