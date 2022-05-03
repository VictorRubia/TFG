package com.victorrubia.tfg.data.repository.user.datasourceImpl

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

class UserCacheDataSourceImpl : UserCacheDataSource {

    private var user : User? = null

    override suspend fun getUserFromCache(): User? {
        return user
    }

    override suspend fun saveUserToCache(user: User) {
        this.user = user
    }

    override suspend fun clearAll() {
        this.user = null
    }

}