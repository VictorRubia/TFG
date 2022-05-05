package com.victorrubia.tfg.presentation.di.feelings_menu

import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FeelingsMenuModule {

    @FeelingsMenuScope
    @Provides
    fun provideFeelingsMenuViewModelFactory(getCurrentActivityUseCase: GetCurrentActivityUseCase,
                                            addTagUseCase: AddTagUseCase) : FeelingsMenuViewModelFactory{
        return FeelingsMenuViewModelFactory(getCurrentActivityUseCase, addTagUseCase)
    }

}