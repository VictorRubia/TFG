package com.victorrubia.tfg.presentation.activity_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.presentation.home.HomeViewModel

class ActivityConfirmationViewModelFactory(
    private val newActivityUseCase : NewActivityUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityConfirmationViewModel(newActivityUseCase) as T
    }
}