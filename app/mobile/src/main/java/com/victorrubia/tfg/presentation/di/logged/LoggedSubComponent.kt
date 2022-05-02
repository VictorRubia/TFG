package com.victorrubia.tfg.presentation.di.logged

import com.victorrubia.tfg.presentation.logged.LoggedActivity
import dagger.Subcomponent

@LoggedScope
@Subcomponent(modules = [LoggedModule::class])
interface LoggedSubComponent {
    fun inject(loggedActivity: LoggedActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : LoggedSubComponent
    }
}