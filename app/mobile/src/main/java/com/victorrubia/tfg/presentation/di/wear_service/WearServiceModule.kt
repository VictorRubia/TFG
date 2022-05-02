package com.victorrubia.tfg.presentation.di.wear_service

import com.victorrubia.tfg.data.api.WearService
import com.victorrubia.tfg.domain.repository.UserRepository
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides

@Module
class WearServiceModule {
    @WearServiceScope
    @Provides
    fun provideWearService(): WearService{
        return WearService()
    }
}