package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(newActivityUseCase: NewActivityUseCase) : HomeViewModelFactory{
        return HomeViewModelFactory(newActivityUseCase)
    }
}