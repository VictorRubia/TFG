package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newActivityUseCase: NewActivityUseCase,
    private val requestUserUseCase: RequestUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun newActivity(name: String, startTimestamp: String) = liveData {
        val activity = newActivityUseCase.execute(name, startTimestamp)
        emit(activity)
    }

    fun requestUser(){
        CoroutineScope(Dispatchers.IO).launch {
            requestUserUseCase.execute()
        }
    }

    fun saveUser(user : User){
        CoroutineScope(Dispatchers.IO).launch {
            saveUserUseCase.execute(user)
        }
    }

}