package com.victorrubia.tfg.presentation.di.measuring_menu

import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.presentation.measuring_menu.MeasuringMenuActivity
import com.victorrubia.tfg.presentation.measuring_menu.MeasuringMenuViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MeasuringMenuModule {

    @MeasuringMenuScope
    @Provides
    fun provideMeasuringMenuViewModelFactory(endActivityUseCase: EndActivityUseCase) : MeasuringMenuViewModelFactory{
        return MeasuringMenuViewModelFactory(endActivityUseCase)
    }

}