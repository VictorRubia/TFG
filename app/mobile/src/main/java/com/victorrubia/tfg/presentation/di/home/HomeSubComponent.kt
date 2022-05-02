package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.presentation.home.HomeActivity
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    fun inject(homeActivity: HomeActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : HomeSubComponent
    }
}