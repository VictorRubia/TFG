package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User

interface UserLocalDataSource {
    suspend fun getUserFromDB() : User
    suspend fun saveUserToDB(user: User)
    suspend fun clearAll()
}