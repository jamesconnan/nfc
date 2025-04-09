package com.example.nfcreader.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.nfcreader.data.User
import com.example.nfcreader.data.SecondaryRfidTag
import com.example.nfcreader.viewmodels.UserViewModel
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: UserViewModel, nfcInfo: String) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showRfidDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    
    // Extract NFC ID from nfcInfo
    val nfcId = remember(nfcInfo) {
        if (nfcInfo.startsWith("NFC ID: ")) {
            nfcInfo.substringAfter("NFC ID: ").substringBefore("\n")
        } else {
            ""
        }
    }
    
    // Collect the users from the StateFlow
    val users by viewModel.users.collectAsState()
    val secondaryRfidTags by viewModel.secondaryRfidTags.collectAsState()

    // Update secondary RFID tags when a user is selected
    LaunchedEffect(selectedUser) {
        selectedUser?.let { user ->
            viewModel.loadSecondaryRfidTags(user.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Management") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Add User Button
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add User")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add User")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User List
            if (users.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No users found. Add a new user to get started.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users) { user ->
                        UserCard(
                            user = user,
                            onEditClick = {
                                selectedUser = user
                                showEditDialog = true
                            },
                            onDeleteClick = {
                                selectedUser = user
                                showDeleteConfirmation = true
                            },
                            onRfidClick = {
                                selectedUser = user
                                showRfidDialog = true
                            }
                        )
                    }
                }
            }
        }

        // Add User Dialog
        if (showAddDialog) {
            AddUserDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, surname, idNumber, phoneNumber ->
                    viewModel.addUser(name, surname, idNumber, phoneNumber)
                    showAddDialog = false
                }
            )
        }

        // Edit User Dialog
        if (showEditDialog && selectedUser != null) {
            EditUserDialog(
                user = selectedUser!!,
                onDismiss = { showEditDialog = false },
                onConfirm = { name, surname, idNumber, phoneNumber, balance ->
                    viewModel.updateUser(selectedUser!!.copy(
                        name = name,
                        surname = surname,
                        idNumber = idNumber,
                        phoneNumber = phoneNumber,
                        balance = balance
                    ))
                    showEditDialog = false
                }
            )
        }

        // RFID Management Dialog
        if (showRfidDialog && selectedUser != null) {
            RfidManagementDialog(
                user = selectedUser!!,
                rfidTags = secondaryRfidTags,
                nfcId = nfcId,
                onDismiss = { showRfidDialog = false },
                onAddRfid = { tagId ->
                    viewModel.addSecondaryRfidTag(selectedUser!!.id, tagId)
                },
                onDeleteRfid = { tag ->
                    viewModel.deleteSecondaryRfidTag(tag)
                }
            )
        }

        // Delete Confirmation Dialog
        if (showDeleteConfirmation && selectedUser != null) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete User") },
                text = { Text("Are you sure you want to delete this user?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteUser(selectedUser!!)
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    user: User,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRfidClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${user.name} ${user.surname}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "ID: ${user.idNumber}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Phone: ${user.phoneNumber}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Balance: R${user.balance}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    IconButton(onClick = onRfidClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Manage RFID Tags")
                    }
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit User")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete User")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var showIdNumberError by remember { mutableStateOf(false) }
    var showPhoneError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New User") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Surname") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = idNumber,
                    onValueChange = { 
                        idNumber = it
                        showIdNumberError = !isValidIdNumber(it)
                    },
                    label = { Text("ID Number") },
                    isError = showIdNumberError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showIdNumberError) {
                    Text(
                        text = "ID number must be 13 digits",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { 
                        phoneNumber = it
                        showPhoneError = !isValidPhoneNumber(it)
                    },
                    label = { Text("Phone Number") },
                    isError = showPhoneError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showPhoneError) {
                    Text(
                        text = "Phone number must be 10 digits",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isValidIdNumber(idNumber) && isValidPhoneNumber(phoneNumber)) {
                        onConfirm(name, surname, idNumber, phoneNumber)
                    }
                },
                enabled = name.isNotBlank() && surname.isNotBlank() && 
                         isValidIdNumber(idNumber) && isValidPhoneNumber(phoneNumber)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserDialog(
    user: User,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, Double) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var surname by remember { mutableStateOf(user.surname) }
    var idNumber by remember { mutableStateOf(user.idNumber) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var showIdNumberError by remember { mutableStateOf(false) }
    var showPhoneError by remember { mutableStateOf(false) }
    var balanceAdjustment by remember { mutableStateOf("") }
    var showBalanceError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit User") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Surname") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = idNumber,
                    onValueChange = { 
                        idNumber = it
                        showIdNumberError = !isValidIdNumber(it)
                    },
                    label = { Text("ID Number") },
                    isError = showIdNumberError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showIdNumberError) {
                    Text(
                        text = "ID number must be 13 digits",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { 
                        phoneNumber = it
                        showPhoneError = !isValidPhoneNumber(it)
                    },
                    label = { Text("Phone Number") },
                    isError = showPhoneError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showPhoneError) {
                    Text(
                        text = "Phone number must be 10 digits",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                // Balance Adjustment Section
                Text(
                    text = "Current Balance: R${user.balance}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = balanceAdjustment,
                    onValueChange = { 
                        balanceAdjustment = it
                        showBalanceError = !isValidBalanceAdjustment(it)
                    },
                    label = { Text("Amount to Add/Subtract") },
                    isError = showBalanceError,
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text("R") }
                )
                if (showBalanceError) {
                    Text(
                        text = "Please enter a valid amount",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (isValidBalanceAdjustment(balanceAdjustment)) {
                                val adjustment = balanceAdjustment.toDouble()
                                onConfirm(name, surname, idNumber, phoneNumber, user.balance + adjustment)
                            }
                        },
                        enabled = isValidBalanceAdjustment(balanceAdjustment)
                    ) {
                        Text("Add Amount")
                    }
                    Button(
                        onClick = {
                            if (isValidBalanceAdjustment(balanceAdjustment)) {
                                val adjustment = balanceAdjustment.toDouble()
                                onConfirm(name, surname, idNumber, phoneNumber, user.balance - adjustment)
                            }
                        },
                        enabled = isValidBalanceAdjustment(balanceAdjustment)
                    ) {
                        Text("Subtract Amount")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isValidIdNumber(idNumber) && isValidPhoneNumber(phoneNumber)) {
                        onConfirm(name, surname, idNumber, phoneNumber, user.balance)
                    }
                },
                enabled = name.isNotBlank() && surname.isNotBlank() && 
                         isValidIdNumber(idNumber) && isValidPhoneNumber(phoneNumber)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RfidManagementDialog(
    user: User,
    rfidTags: List<SecondaryRfidTag>,
    nfcId: String,
    onDismiss: () -> Unit,
    onAddRfid: (String) -> Unit,
    onDeleteRfid: (SecondaryRfidTag) -> Unit
) {
    var newRfidTag by remember { mutableStateOf(nfcId) }
    var showError by remember { mutableStateOf(false) }

    // Update newRfidTag when nfcId changes
    LaunchedEffect(nfcId) {
        if (nfcId.isNotEmpty()) {
            newRfidTag = nfcId
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Manage RFID Tags") },
        text = {
            Column {
                Text(
                    text = "User: ${user.name} ${user.surname}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Add new RFID tag
                OutlinedTextField(
                    value = newRfidTag,
                    onValueChange = { 
                        newRfidTag = it
                        showError = !isValidRfidTag(it)
                    },
                    label = { Text("New RFID Tag") },
                    isError = showError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showError) {
                    Text(
                        text = "RFID tag must be a valid hexadecimal string",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    onClick = {
                        if (isValidRfidTag(newRfidTag)) {
                            onAddRfid(newRfidTag)
                            newRfidTag = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValidRfidTag(newRfidTag)
                ) {
                    Text("Add RFID Tag")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // List of existing RFID tags
                Text(
                    text = "Existing RFID Tags:",
                    style = MaterialTheme.typography.titleSmall
                )
                LazyColumn {
                    items(rfidTags) { tag ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(tag.tagId)
                            IconButton(onClick = { onDeleteRfid(tag) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete RFID Tag")
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}

private fun isValidIdNumber(idNumber: String): Boolean {
    return idNumber.length == 13 && idNumber.all { it.isDigit() }
}

private fun isValidPhoneNumber(phoneNumber: String): Boolean {
    return phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }
}

private fun isValidRfidTag(tag: String): Boolean {
    return tag.isNotBlank() && tag.all { it.isLetterOrDigit() }
}

private fun isValidBalanceAdjustment(amount: String): Boolean {
    return try {
        val value = amount.toDouble()
        value > 0
    } catch (e: NumberFormatException) {
        false
    }
} 