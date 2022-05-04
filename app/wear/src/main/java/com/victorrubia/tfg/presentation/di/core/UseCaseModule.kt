package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.domain.repository.ActivityRepository
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository
import com.victorrubia.tfg.domain.repository.UserRepository
import com.victorrubia.tfg.domain.usecase.*
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

    @Provides
    fun provideRequestUserUseCase(userRepository: UserRepository) : RequestUserUseCase{
        return RequestUserUseCase(userRepository)
    }

    @Provides
    fun provideSaveUserUseCase(userRepository: UserRepository) : SaveUserUseCase {
        return SaveUserUseCase(userRepository)
    }

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun provideSavePPGMeasureUseCase(ppgMeasureRepository: PPGMeasureRepository) : SavePPGMeasureUseCase {
        return SavePPGMeasureUseCase(ppgMeasureRepository)
    }

    @Provides
    fun provideEndPPGMeasureUseCase(ppgMeasureRepository: PPGMeasureRepository) : EndPPGMeasureUseCase {
        return EndPPGMeasureUseCase(ppgMeasureRepository)
    }

}