package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(): User? = userRepository.getUser()

}