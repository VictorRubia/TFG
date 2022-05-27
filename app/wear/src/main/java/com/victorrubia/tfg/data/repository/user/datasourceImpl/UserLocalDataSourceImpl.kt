package com.victorrubia.tfg.data.repository.user.datasourceImpl

import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [UserLocalDataSource] interface for retrieving user data from the local database.
 * (i.e. Room database)
 *
 * @property userDao The DAO for the [User] table.
 */
class UserLocalDataSourceImpl(
    private val userDao : UserDao
) : UserLocalDataSource{

    /**
     * {@inheritDoc}
     */
    override suspend fun getUserFromDB(): User {
        return userDao.getUser()
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun saveUserToDB(user: User) {
        CoroutineScope(Dispatchers.IO).launch{
            userDao.saveUser(user)
        }
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUser()
        }
    }
}