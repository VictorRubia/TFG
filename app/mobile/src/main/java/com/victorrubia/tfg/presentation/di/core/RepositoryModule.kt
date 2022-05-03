package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfg.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDatasource: UserRemoteDatasource,
        userLocalDataSource: UserLocalDataSource,
        userCacheDataSource: UserCacheDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDatasource,
            userLocalDataSource,
            userCacheDataSource,
        )
    }
}