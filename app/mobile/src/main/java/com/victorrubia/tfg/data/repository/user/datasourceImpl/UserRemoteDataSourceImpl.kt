package com.victorrubia.tfg.data.repository.user.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val tfgService : TFGService
) : UserRemoteDatasource {
    override suspend fun getUser(email: String, password: String): Response<User> = tfgService.getUserInfo(email,password)

    override suspend fun rememberPassword(email: String) = tfgService.requestPasswordRecovery(email)
}