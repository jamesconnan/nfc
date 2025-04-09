package com.example.nfcreader.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfcreader.data.MenuItem
import com.example.nfcreader.data.Transaction
import com.example.nfcreader.viewmodels.TransactViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactScreen(
    viewModel: TransactViewModel,
    onNavigateToPayment: () -> Unit
) {
    val total by viewModel.total.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val menuItems by viewModel.menuItems.collectAsState()
    val drinkCounts by viewModel.drinkCounts.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Total Amount Display
        Text(
            text = "Total: R${String.format("%.2f", total)}",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Control Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.clearTotal() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Clear")
            }
            Button(
                onClick = { viewModel.undoLast() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Undo")
            }
            Button(
                onClick = { onNavigateToPayment() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text("Pay")
            }
        }

        // Menu Section
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (menuItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading menu items...",
                    color = Color.Gray
                )
            }
        } else {
            if (selectedCategory == null) {
                // Show categories
                Column {
                    menuItems.distinctBy { it.category }.forEach { item: MenuItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedCategory = item.category },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = item.category,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            } else {
                // Show back button and items for selected category
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { selectedCategory = null }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to categories"
                        )
                    }
                    Text(
                        text = selectedCategory!!,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                
                Column {
                    menuItems.filter { it.category == selectedCategory }.forEach { item: MenuItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { 
                                    viewModel.addDrink(item.name, item.price)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Selected: ${drinkCounts[item.name] ?: 0}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                                Text(
                                    text = "R${String.format("%.2f", item.price)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Denomination Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // First row with smaller denominations
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(1.0, 2.0, 5.0).forEach { amount: Double ->
                    Button(
                        onClick = { viewModel.addAmount(amount) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text("R$amount")
                    }
                }
            }
            
            // Second row with medium denominations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(10.0, 20.0, 50.0).forEach { amount: Double ->
                    Button(
                        onClick = { viewModel.addAmount(amount) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text("R$amount")
                    }
                }
            }
            // Third row with larger denominations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(100.0, 200.0).forEach { amount: Double ->
                    Button(
                        onClick = { viewModel.addAmount(amount) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text("R$amount")
                    }
                }
            }
        }
        

        // Transaction History
        Text(
            text = "Transaction Summary",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Column {
            transactions.forEach { transaction: Transaction ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "R${String.format("%.2f", transaction.amount)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                       Text(
                            text = "${transaction.drinkName}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                .format(Date(transaction.timestamp)),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
} 