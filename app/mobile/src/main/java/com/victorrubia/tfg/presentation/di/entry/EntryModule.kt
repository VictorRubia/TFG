package com.victorrubia.tfg.presentation.di.entry

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.presentation.entry.EntryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class EntryModule {
    @EntryScope
    @Provides
    fun provideEntryViewModelFactory(
        getUserUseCase: GetUserUseCase
    ): EntryViewModelFactory{
        return EntryViewModelFactory(
            getUserUseCase
        )
    }
}