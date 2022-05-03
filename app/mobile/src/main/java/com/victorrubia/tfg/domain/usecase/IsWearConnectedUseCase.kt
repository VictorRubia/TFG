package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.UserRepository

class IsWearConnectedUseCase (private val userRepository: UserRepository) {

    suspend fun execute() = userRepository.isWearConnected()

}