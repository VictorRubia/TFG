package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.user.User

interface UserRepository {

    suspend fun requestUser()

    suspend fun getUser() : User?

    suspend fun saveUser(user : User)
}