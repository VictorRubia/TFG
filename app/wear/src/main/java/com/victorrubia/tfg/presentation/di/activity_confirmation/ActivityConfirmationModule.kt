package com.victorrubia.tfg.presentation.di.activity_confirmation

import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityConfirmationModule {
    @ActivityConfirmationScope
    @Provides
    fun provideActivityConfirmationViewModelFactory(newActivityUseCase: NewActivityUseCase) : ActivityConfirmationViewModelFactory {
        return ActivityConfirmationViewModelFactory(newActivityUseCase)
    }
}