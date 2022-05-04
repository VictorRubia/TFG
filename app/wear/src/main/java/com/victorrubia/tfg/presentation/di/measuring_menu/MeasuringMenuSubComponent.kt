package com.victorrubia.tfg.presentation.di.measuring_menu

import com.victorrubia.tfg.presentation.measuring_menu.MeasuringMenuActivity
import dagger.Subcomponent

@MeasuringMenuScope
@Subcomponent(modules = [MeasuringMenuModule::class])
interface MeasuringMenuSubComponent {
    fun inject(measuringMenuActivity: MeasuringMenuActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : MeasuringMenuSubComponent
    }
}