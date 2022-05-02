package com.victorrubia.tfg.presentation.di.entry

import com.victorrubia.tfg.presentation.entry.EntryActivity
import dagger.Subcomponent

@EntryScope
@Subcomponent(modules = [EntryModule::class])
interface EntrySubComponent {
    fun inject(entryActivity: EntryActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : EntrySubComponent
    }

}