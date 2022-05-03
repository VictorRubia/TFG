package com.victorrubia.tfg.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.model.user.User

interface UserRepository {

    suspend fun getUser(email : String = "", password : String = "") : User?

    suspend fun removeLocalUser()

    suspend fun requestPasswordReminder(email : String) : Boolean

    suspend fun sendApiKeyToWear()

    suspend fun isWearConnected() : Boolean?

}