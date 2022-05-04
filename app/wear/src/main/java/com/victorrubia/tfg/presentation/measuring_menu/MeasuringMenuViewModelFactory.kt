package com.victorrubia.tfg.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase

class MeasuringMenuViewModelFactory(
    private val endActivityUseCase: EndActivityUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeasuringMenuViewModel(endActivityUseCase) as T
    }
}