package com.example.nfcreader.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfcreader.viewmodels.TransactViewModel
import androidx.compose.material.MaterialTheme

@Composable
fun TransactScreen(viewModel: TransactViewModel = viewModel()) {
    val total by viewModel.total.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Display total
        Text(text = "Total: R$total", style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.height(16.dp))

        // Denomination buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf(5, 10, 20, 50, 100).forEach { denomination ->
                Button(onClick = { viewModel.addAmount(denomination.toDouble()) }) {
                    Text("R$denomination")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Clear and Undo buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { viewModel.clearTotal() }) {
                Text("Clear")
            }
            Button(onClick = { viewModel.undoLast() }) {
                Text("Undo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction summary list
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                Text("${transaction.amount} at ${transaction.timestamp}")
            }
        }
    }
} 