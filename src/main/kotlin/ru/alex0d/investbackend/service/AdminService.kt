package ru.alex0d.investbackend.service

import org.springframework.stereotype.Service
import ru.alex0d.investbackend.dto.UpdateUserDto
import ru.alex0d.investbackend.model.Role
import ru.alex0d.investbackend.model.User
import ru.alex0d.investbackend.repository.UserRepository

@Service
class AdminService(
    private val userRepository: UserRepository,
) {
    fun getUsers(): List<User> = userRepository.findAll()

    fun updateUser(id: Int, updateUserDto: UpdateUserDto) {
        val user = userRepository.findById(id).get()
        updateUserDto.firstname?.let { user.firstname = it }
        updateUserDto.lastname?.let { user.lastname = it }
        updateUserDto.email?.let { user.email = it }
        updateUserDto.role?.let { user.role = Role.valueOf(it) }
        userRepository.save(user)
    }

    fun deleteUser(id: Int) = userRepository.deleteById(id)
}