package com.victorrubia.tfg.presentation.recover_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase

class RecoverPasswordViewModel(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    fun requestPasswordReminder(email : String) = liveData {
        val confirmation = recoverPasswordUseCase.execute(email)
        emit(confirmation)
    }
}