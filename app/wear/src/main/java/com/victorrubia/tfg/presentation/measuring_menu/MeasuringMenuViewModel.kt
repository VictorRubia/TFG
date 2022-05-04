package com.victorrubia.tfg.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.EndActivityUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase

class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
) : ViewModel() {


    fun endActivity() = liveData{
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

}