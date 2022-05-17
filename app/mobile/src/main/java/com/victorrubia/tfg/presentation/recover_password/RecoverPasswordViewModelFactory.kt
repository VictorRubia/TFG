package com.victorrubia.tfg.presentation.recover_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase

class RecoverPasswordViewModelFactory(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return RecoverPasswordViewModel(recoverPasswordUseCase) as T
    }

}