package com.victorrubia.tfg.data.repository.user.datasource

interface UserRemoteDataSource {

    suspend fun requestUser()

}