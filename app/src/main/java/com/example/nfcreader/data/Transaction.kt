package com.example.nfcreader.data

data class Transaction(
    val amount: Double,
    val timestamp: Long,
    val drinkName: String? = null
) 