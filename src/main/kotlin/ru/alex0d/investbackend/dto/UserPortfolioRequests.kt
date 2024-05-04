package ru.alex0d.investbackend.dto

data class BuyStockRequest(
    var ticker: String,
    var amount: Int
)

data class SellStockRequest(
    var ticker: String,
    var amount: Int
)