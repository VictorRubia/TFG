package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User

interface UserCacheDataSource {
    suspend fun getUserFromCache() : User?
    suspend fun saveUserToCache(user : User)
    suspend fun clearAll()
}