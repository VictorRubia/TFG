package com.victorrubia.tfg.presentation.logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase

class LoggedViewModelFactory(
private val getUserUseCase : GetUserUseCase,
private val removeLocalUserUseCase: RemoveLocalUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoggedViewModel(getUserUseCase, removeLocalUserUseCase) as T
    }
}