package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

class SaveUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(user : User) = userRepository.saveUser(user)

}