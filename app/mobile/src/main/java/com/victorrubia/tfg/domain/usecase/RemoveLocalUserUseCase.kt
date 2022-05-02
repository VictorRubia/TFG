package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.UserRepository

class RemoveLocalUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute() = userRepository.removeLocalUser()

}