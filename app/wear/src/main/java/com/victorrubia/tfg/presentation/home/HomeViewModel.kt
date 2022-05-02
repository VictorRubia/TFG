package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase

class HomeViewModel(
    private val newActivityUseCase: NewActivityUseCase,
) : ViewModel() {

    fun newActivity(name: String, startTimestamp: String) = liveData {
        val activity = newActivityUseCase.execute(name, startTimestamp)
        emit(activity)
    }

}