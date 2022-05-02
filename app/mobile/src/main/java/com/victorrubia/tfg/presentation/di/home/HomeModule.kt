package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(getUserUseCase: GetUserUseCase): HomeViewModelFactory {
        return HomeViewModelFactory(getUserUseCase)
    }
}