package ru.alex0d.investbackend.service

import org.springframework.stereotype.Service
import ru.alex0d.investbackend.dto.BuyStockRequest
import ru.alex0d.investbackend.dto.PortfolioInfoDto
import ru.alex0d.investbackend.dto.SellStockRequest
import ru.alex0d.investbackend.repository.PortfolioRepository

@Service
class PortfolioService(
    private val portfolioRepository: PortfolioRepository
) {
    fun getPortfolio(): PortfolioInfoDto {
        TODO("Not yet implemented")
    }

    fun buyStock(buyStockRequest: BuyStockRequest): Boolean {
        TODO("Not yet implemented")
    }

    fun sellStock(sellStockRequest: SellStockRequest): Boolean {
        TODO("Not yet implemented")
    }
}