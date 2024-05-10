package ru.alex0d.investbackend.dto

data class BuyStockRequest(
    var uid: String,
    var amount: Int
)

data class SellStockRequest(
    var uid: String,
    var amount: Int
)