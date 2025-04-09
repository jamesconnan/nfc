package com.example.nfcreader.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcreader.data.MenuItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class TransactViewModel(application: Application) : AndroidViewModel(application) {
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    init {
        loadMenuItems()
    }

    private fun loadMenuItems() {
        viewModelScope.launch {
            try {
                Log.d("TransactViewModel", "Attempting to load menu items")
                val inputStream = getApplication<Application>().assets.open("drinks_menu.json")
                val reader = InputStreamReader(inputStream)
                val jsonString = reader.readText()
                Log.d("TransactViewModel", "Loaded JSON: $jsonString")
                
                val menuData = Gson().fromJson(jsonString, Map::class.java)
                Log.d("TransactViewModel", "Parsed menu data: $menuData")
                
                val items = mutableListOf<MenuItem>()
                (menuData["drinks"] as? List<Map<String, Any>>)?.forEach { drink ->
                    try {
                        val name = drink["name"] as? String ?: "Unknown"
                        val price = (drink["price"] as? Number)?.toDouble() ?: 0.0
                        val category = drink["category"] as? String ?: "Uncategorized"
                        
                        items.add(
                            MenuItem(
                                name = name,
                                price = price,
                                category = category
                            )
                        )
                        Log.d("TransactViewModel", "Added menu item: $name, R$$price, $category")
                    } catch (e: Exception) {
                        Log.e("TransactViewModel", "Error parsing drink item: $drink", e)
                    }
                }
                
                if (items.isEmpty()) {
                    Log.e("TransactViewModel", "No menu items were loaded")
                } else {
                    Log.d("TransactViewModel", "Successfully loaded ${items.size} menu items")
                }
                
                _menuItems.value = items
            } catch (e: Exception) {
                Log.e("TransactViewModel", "Error loading menu items", e)
                e.printStackTrace()
            }
        }
    }

    fun addAmount(amount: Double) {
        _total.value += amount
        _transactions.value = _transactions.value + Transaction(amount, System.currentTimeMillis())
    }

    fun clearTotal() {
        _total.value = 0.0
        _transactions.value = emptyList()
    }

    fun undoLast() {
        val currentTransactions = _transactions.value.toMutableList()
        if (currentTransactions.isNotEmpty()) {
            val lastTransaction = currentTransactions.removeAt(currentTransactions.size - 1)
            _total.value -= lastTransaction.amount
            _transactions.value = currentTransactions
        }
    }

    data class Transaction(
        val amount: Double,
        val timestamp: Long
    )
} 