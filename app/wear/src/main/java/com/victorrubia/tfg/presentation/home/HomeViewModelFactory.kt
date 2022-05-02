package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase

class HomeViewModelFactory(
    private val newActivityUseCase : NewActivityUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(newActivityUseCase) as T
    }
}