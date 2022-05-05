package com.victorrubia.tfg.presentation.di

import android.app.Application
import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent

interface Injector {
    fun createHomeSubComponent() : HomeSubComponent
    fun createActivityConfirmationSubComponent() : ActivityConfirmationSubComponent
    fun createMeasuringMenuSubComponent() : MeasuringMenuSubComponent
    fun createFeelingsMenuSubComponent() : FeelingsMenuSubComponent
}