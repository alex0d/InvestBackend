package ru.alex0d.investbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.alex0d.investbackend.model.Portfolio
import ru.alex0d.investbackend.model.User

@Repository
interface PortfolioRepository : JpaRepository<Portfolio, Int> {
    fun getPortfolioByUser(user: User): Portfolio
}