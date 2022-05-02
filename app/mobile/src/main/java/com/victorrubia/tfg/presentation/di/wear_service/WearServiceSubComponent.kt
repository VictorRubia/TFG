package com.victorrubia.tfg.presentation.di.wear_service

import com.victorrubia.tfg.data.api.WearService
import dagger.Subcomponent

@WearServiceScope
@Subcomponent(modules = [WearServiceModule::class])
interface WearServiceSubComponent {
    fun inject(wearService : WearService)

    @Subcomponent.Factory
    interface Factory{
        fun create() : WearServiceSubComponent
    }
}