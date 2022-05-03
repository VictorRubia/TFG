package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

class RequestUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute() = userRepository.requestUser()
}