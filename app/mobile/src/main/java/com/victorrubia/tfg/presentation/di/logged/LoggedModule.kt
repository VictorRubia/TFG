package com.victorrubia.tfg.presentation.di.logged

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase
import com.victorrubia.tfg.presentation.logged.LoggedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoggedModule {
    @LoggedScope
    @Provides
    fun provideLoggedViewModelFactory(
        getUserUseCase: GetUserUseCase,
        removeLocalUserUseCase: RemoveLocalUserUseCase,
        sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
        isWearConnectedUseCase: IsWearConnectedUseCase
    ): LoggedViewModelFactory{
        return LoggedViewModelFactory(
            getUserUseCase,
            removeLocalUserUseCase,
            sendApiKeyToWearUseCase,
            isWearConnectedUseCase
        )
    }
}