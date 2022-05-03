package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import com.victorrubia.tfg.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(newActivityUseCase: NewActivityUseCase,
                                    requestUserUseCase: RequestUserUseCase,
                                    saveUserUseCase: SaveUserUseCase,
                                    getUserUseCase: GetUserUseCase) : HomeViewModelFactory{
        return HomeViewModelFactory(newActivityUseCase, requestUserUseCase, saveUserUseCase, getUserUseCase)
    }
}