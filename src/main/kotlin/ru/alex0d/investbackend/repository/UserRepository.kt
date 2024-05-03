package ru.alex0d.investbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.alex0d.investbackend.model.User

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}