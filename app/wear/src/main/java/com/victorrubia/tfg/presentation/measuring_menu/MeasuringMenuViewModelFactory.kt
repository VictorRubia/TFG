package com.victorrubia.tfg.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.EndPPGMeasureUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.domain.usecase.SavePPGMeasureUseCase

class MeasuringMenuViewModelFactory(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeasuringMenuViewModel(endActivityUseCase, getCurrentActivityUseCase, savePPGMeasureUseCase, endPPGMeasureUseCase) as T
    }
}