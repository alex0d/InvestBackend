package ru.alex0d.investbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.alex0d.investbackend.dto.BuyStockRequest
import ru.alex0d.investbackend.dto.PortfolioInfoDto
import ru.alex0d.investbackend.dto.SellStockRequest
import ru.alex0d.investbackend.service.PortfolioService

@RestController
@RequestMapping("/api/portfolio")
class PortfolioController(
    private val portfolioService: PortfolioService
) {
    @GetMapping
    fun getPortfolio(): ResponseEntity<PortfolioInfoDto> {
        val portfolio = portfolioService.getPortfolio()
        return ResponseEntity.ok(portfolio)
    }

    @PostMapping("/buy")
    fun buyStock(@RequestBody buyStockRequest: BuyStockRequest): ResponseEntity<String> {
        return if (buyStockRequest.amount <= 0) {
            ResponseEntity.badRequest().body("Amount must be positive")
        } else if (buyStockRequest.ticker.isBlank()) {
            ResponseEntity.badRequest().body("Ticker must not be blank")
        } else if (portfolioService.buyStock(buyStockRequest)) {
            ResponseEntity.ok("Stock bought")
        } else {
            ResponseEntity.badRequest().body("Error buying stock")
        }
    }

    @PostMapping("/sell")
    fun sellStock(@RequestBody sellStockRequest: SellStockRequest): ResponseEntity<String> {
        return if (sellStockRequest.amount <= 0) {
            ResponseEntity.badRequest().body("Amount must be positive")
        } else if (sellStockRequest.ticker.isBlank()) {
            ResponseEntity.badRequest().body("Ticker must not be blank")
        } else if (portfolioService.sellStock(sellStockRequest)) {
            ResponseEntity.ok("Stock sold")
        } else {
            ResponseEntity.badRequest().body("Error selling stock")
        }
    }
}