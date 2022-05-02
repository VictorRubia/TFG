package com.victorrubia.tfg.presentation.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

class EntryViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun getUser() = liveData {
        val user = getUserUseCase.execute("", "")
        emit(user)
    }
}