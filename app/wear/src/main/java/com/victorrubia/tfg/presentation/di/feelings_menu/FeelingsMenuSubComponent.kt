package com.victorrubia.tfg.presentation.di.feelings_menu

import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuActivity
import dagger.Subcomponent

@FeelingsMenuScope
@Subcomponent(modules = [FeelingsMenuModule::class])
interface FeelingsMenuSubComponent {
    fun inject(feelingsMenuActivity: FeelingsMenuActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : FeelingsMenuSubComponent
    }

}