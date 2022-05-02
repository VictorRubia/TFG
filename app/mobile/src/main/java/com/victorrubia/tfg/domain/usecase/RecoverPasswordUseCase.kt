package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.UserRepository

class RecoverPasswordUseCase(private val userRepository: UserRepository) {

    suspend fun execute(email : String) : Boolean = userRepository.requestPasswordReminder(email)

}