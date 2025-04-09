package com.example.nfcreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class Transaction(val amount: Double, val timestamp: String)

class TransactViewModel : ViewModel() {
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val transactionHistory = mutableListOf<Transaction>()

    fun addAmount(amount: Double) {
        _total.value += amount
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val transaction = Transaction(amount, timestamp)
        transactionHistory.add(transaction)
        _transactions.value = transactionHistory.toList()
    }

    fun clearTotal() {
        _total.value = 0.0
        transactionHistory.clear()
        _transactions.value = transactionHistory.toList()
    }

    fun undoLast() {
        if (transactionHistory.isNotEmpty()) {
            val lastTransaction = transactionHistory.removeAt(transactionHistory.size - 1)
            _total.value -= lastTransaction.amount
            _transactions.value = transactionHistory.toList()
        }
    }
} 