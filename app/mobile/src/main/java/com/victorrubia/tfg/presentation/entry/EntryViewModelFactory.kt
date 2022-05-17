package com.victorrubia.tfg.presentation.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

class EntryViewModelFactory(
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EntryViewModel(getUserUseCase) as T
    }
}