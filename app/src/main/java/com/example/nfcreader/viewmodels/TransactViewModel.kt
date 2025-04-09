package com.example.nfcreader.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcreader.data.MenuItem
import com.example.nfcreader.data.Transaction
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class TransactViewModel(application: Application) : AndroidViewModel(application) {
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    private val _drinkCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val drinkCounts: StateFlow<Map<String, Int>> = _drinkCounts.asStateFlow()

    init {
        loadMenuItems()
    }

    private fun loadMenuItems() {
        viewModelScope.launch {
            try {
                Log.d("TransactViewModel", "Loading menu items...")
                val inputStream = getApplication<Application>().assets.open("drinks_menu.json")
                val reader = BufferedReader(InputStreamReader(inputStream))
                val json = reader.use { it.readText() }
                Log.d("TransactViewModel", "Loaded JSON: $json")

                val menuData = Gson().fromJson(json, MenuData::class.java)
                Log.d("TransactViewModel", "Parsed menu data: $menuData")

                if (menuData.drinks.isNotEmpty()) {
                    _menuItems.value = menuData.drinks
                    Log.d("TransactViewModel", "Menu items loaded: ${_menuItems.value.size}")
                } else {
                    Log.e("TransactViewModel", "No menu items loaded")
                }
            } catch (e: Exception) {
                Log.e("TransactViewModel", "Error loading menu items", e)
            }
        }
    }

    fun addAmount(amount: Double) {
        _total.value += amount
        _transactions.value = _transactions.value + Transaction(amount, System.currentTimeMillis())
    }

    fun addDrink(drinkName: String, price: Double) {
        val currentCount = _drinkCounts.value[drinkName] ?: 0
        _drinkCounts.value = _drinkCounts.value + (drinkName to (currentCount + 1))
        _total.value += price
        _transactions.value = _transactions.value + Transaction(price, System.currentTimeMillis(), drinkName)
    }

    fun clearTotal() {
        _total.value = 0.0
        _transactions.value = emptyList()
        _drinkCounts.value = emptyMap()
    }

    fun payTotal() {
        _total.value = 0.0
        _transactions.value = emptyList()
        _drinkCounts.value = emptyMap()
    }
    fun undoLast() {
        val currentTransactions = _transactions.value.toMutableList()
        if (currentTransactions.isNotEmpty()) {
            val lastTransaction = currentTransactions.removeAt(currentTransactions.size - 1)
            _total.value -= lastTransaction.amount
            
            // If the transaction was for a drink, decrement its count
            lastTransaction.drinkName?.let { drinkName ->
                val currentCount = _drinkCounts.value[drinkName] ?: 0
                if (currentCount > 0) {
                    _drinkCounts.value = _drinkCounts.value + (drinkName to (currentCount - 1))
                }
            }
            
            _transactions.value = currentTransactions
        }
    }

    private data class MenuData(
        val drinks: List<MenuItem>
    )
} 