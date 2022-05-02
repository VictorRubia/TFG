package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    fun getUser(email : String = "", password : String = "") = liveData {
        val user = getUserUseCase.execute(email, password)
        emit(user)
    }

}