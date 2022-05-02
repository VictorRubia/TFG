package com.victorrubia.tfg.presentation.di

import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent

interface Injector {
    fun createEntrySubComponent(): EntrySubComponent
    fun createHomeSubComponent(): HomeSubComponent
    fun createLoggedSubComponent(): LoggedSubComponent
    fun createRecoverPasswordSubComponent(): RecoverPasswordSubComponent
    fun createWearServiceSubComponent(): WearServiceSubComponent
}