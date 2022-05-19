package com.victorrubia.tfg.presentation.feelings_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase

class FeelingsMenuViewModelFactory(
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val addTagUseCase: AddTagUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeelingsMenuViewModel(getCurrentActivityUseCase, addTagUseCase) as T
    }
}