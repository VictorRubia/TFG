package com.victorrubia.tfg.data.repository.user.datasourceImpl

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of [UserCacheDataSource] interface for retrieving and saving data from/to cache
 */
class UserCacheDataSourceImpl : UserCacheDataSource {

    /**
     * Cache for storing the user
     */
    private var user : User? = null

    /**
     * {@inheritDoc}
     */
    override suspend fun getUserFromCache(): User? {
        return user
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun saveUserToCache(user: User) {
        this.user = user
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun clearAll() {
        this.user = null
    }

}