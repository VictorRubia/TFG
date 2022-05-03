package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.domain.repository.UserRepository
import com.victorrubia.tfg.domain.usecase.*
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

    @Provides
    fun provideSendApiKeyToWeardUseCase(userRepository: UserRepository) : SendApiKeyToWearUseCase{
        return SendApiKeyToWearUseCase(userRepository)
    }

    @Provides
    fun provideIsWearConnecteddUseCase(userRepository: UserRepository) : IsWearConnectedUseCase {
        return IsWearConnectedUseCase(userRepository)
    }

}