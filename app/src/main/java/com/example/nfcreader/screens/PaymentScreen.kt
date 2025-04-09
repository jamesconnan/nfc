package com.example.nfcreader.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfcreader.data.Transaction
import com.example.nfcreader.viewmodels.TransactViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun PaymentScreen(
    totalAmount: Double,
    transactions: List<Transaction>,
    onBack: () -> Unit,
    onCompleteTransaction: () -> Unit
) {
    var showTenderedDialog by remember { mutableStateOf(false) }
    var tenderedAmount by remember { mutableStateOf(TextFieldValue("")) }
    var showChange by remember { mutableStateOf(false) }
    var showBCard by remember { mutableStateOf(false) }
    var change by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Total Amount
        Text(
            text = "Total Amount: R${String.format("%.2f", totalAmount)}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Transaction Summary
        Text(
            text = "Transaction Summary",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        transactions.forEach { transaction ->
            /*val date = Date(transaction.timestamp)
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val timeString = sdf.format(date)
            */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${transaction.drinkName}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "R${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge
                )
                /*Text(
                    text = timeString,
                    style = MaterialTheme.typography.bodyMedium
                )*/
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Payment Options
        Text(
            text = "Payment Options",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Payment buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    showBCard = false;
                    showTenderedDialog = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cash")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (!showBCard) {
                        showBCard = true;
                    } else {
                        // For card payment, we assume exact amount
                        onCompleteTransaction()
                        onBack()
                        showBCard = false;
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (showBCard) "Complete" else "Bank Card")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }

    // Tendered Amount Dialog
    if (showTenderedDialog) {
        Dialog(onDismissRequest = { showTenderedDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 400.dp, max = 400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Enter Amount Tendered",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = tenderedAmount,
                        onValueChange = { 
                            // Only allow numbers and decimal point
                            if (it.text.matches(Regex("^\\d*\\.?\\d*$"))) {
                                tenderedAmount = it
                            }
                        },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textStyle = MaterialTheme.typography.titleLarge
                    )

                    if (showChange) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Change Due",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "R${String.format("%.2f", change)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { 
                                showTenderedDialog = false
                                showChange = false
                                tenderedAmount = TextFieldValue("")
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                val tendered = tenderedAmount.text.toDoubleOrNull() ?: 0.0
                                if (tendered >= totalAmount) {
                                    if (!showChange) {
                                        // First confirmation - show change
                                        change = tendered - totalAmount
                                        showChange = true
                                    } else {
                                        // Second confirmation - complete transaction and close
                                        onCompleteTransaction()
                                        onBack()
                                        showTenderedDialog = false
                                        showChange = false
                                        tenderedAmount = TextFieldValue("")
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (showChange) "Complete" else "Confirm")
                        }
                    }
                }
            }
        }
    }
} 