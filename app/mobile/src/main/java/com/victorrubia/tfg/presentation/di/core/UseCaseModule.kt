package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.domain.repository.UserRepository
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun provideRemoveLocalUserUseCase(userRepository: UserRepository) : RemoveLocalUserUseCase{
        return RemoveLocalUserUseCase(userRepository)
    }

    @Provides
    fun provideRecoverPasswordUseCase(userRepository: UserRepository) : RecoverPasswordUseCase{
        return RecoverPasswordUseCase(userRepository)
    }

}