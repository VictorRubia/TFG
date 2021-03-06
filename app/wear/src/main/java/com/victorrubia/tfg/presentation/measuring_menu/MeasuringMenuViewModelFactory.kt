package com.victorrubia.tfg.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.EndPPGMeasureUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.domain.usecase.SavePPGMeasureUseCase

/**
 * Factory for creating a [MeasuringMenuViewModel] with a constructor that takes a [EndActivityUseCase]
 * [EndPPGMeasureUseCase], [GetCurrentActivityUseCase] and [SavePPGMeasureUseCase].
 *
 * @property endActivityUseCase [EndActivityUseCase]
 * @property endPPGMeasureUseCase [EndPPGMeasureUseCase]
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase]
 * @property savePPGMeasureUseCase [SavePPGMeasureUseCase]
 */
class MeasuringMenuViewModelFactory(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a [MeasuringMenuViewModel]
     *
     * @param modelClass [Class] of the [ViewModel]
     * @return [MeasuringMenuViewModel] instance
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeasuringMenuViewModel(endActivityUseCase, getCurrentActivityUseCase, savePPGMeasureUseCase, endPPGMeasureUseCase) as T
    }
}