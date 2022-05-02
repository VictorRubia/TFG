package com.victorrubia.tfg.presentation.di.recover_password

import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase
import com.victorrubia.tfg.presentation.recover_password.RecoverPasswordViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RecoverPasswordModule {
    @RecoverPasswordScope
    @Provides
    fun provideRecoverPasswordViewModelFactory(
        recoverPasswordUseCase: RecoverPasswordUseCase
    ): RecoverPasswordViewModelFactory{
        return RecoverPasswordViewModelFactory(
            recoverPasswordUseCase
        )
    }
}