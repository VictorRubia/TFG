package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.domain.repository.ActivityRepository
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideNewActivityUseCase(activityRepository: ActivityRepository) : NewActivityUseCase{
        return NewActivityUseCase(activityRepository)
    }

    @Provides
    fun provideGetCurrentActivityUseCase(activityRepository: ActivityRepository) : GetCurrentActivityUseCase {
        return GetCurrentActivityUseCase(activityRepository)
    }

    @Provides
    fun provideEndActivityUseCase(activityRepository: ActivityRepository) : EndActivityUseCase {
        return EndActivityUseCase(activityRepository)
    }

}