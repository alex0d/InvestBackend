package ru.alex0d.investbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.alex0d.investbackend.model.PortfolioStock

@Repository
interface PortfolioStockRepository : JpaRepository<PortfolioStock, Int>