package com.victorrubia.tfg.presentation.logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase

class LoggedViewModelFactory(
private val getUserUseCase : GetUserUseCase,
private val removeLocalUserUseCase: RemoveLocalUserUseCase,
private val sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
private val isWearConnectedUseCase: IsWearConnectedUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoggedViewModel(getUserUseCase, removeLocalUserUseCase, sendApiKeyToWearUseCase, isWearConnectedUseCase) as T
    }
}