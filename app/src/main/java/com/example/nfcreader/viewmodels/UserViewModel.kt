package com.example.nfcreader.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcreader.data.AppDatabase
import com.example.nfcreader.data.User
import com.example.nfcreader.data.UserDao
import com.example.nfcreader.data.SecondaryRfidTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val userDao: UserDao = database.userDao()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _secondaryRfidTags = MutableStateFlow<List<SecondaryRfidTag>>(emptyList())
    val secondaryRfidTags: StateFlow<List<SecondaryRfidTag>> = _secondaryRfidTags.asStateFlow()

    init {
        viewModelScope.launch {
            userDao.getAllUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    fun addUser(name: String, surname: String, idNumber: String, phoneNumber: String) {
        viewModelScope.launch {
            val user = User(
                name = name,
                surname = surname,
                idNumber = idNumber,
                phoneNumber = phoneNumber,
                primaryRfidTag = null,
                balance = 0.0
            )
            userDao.insertUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUser(user)
        }
    }

    suspend fun getUserByIdNumber(idNumber: String): User? {
        return userDao.getUserByIdNumber(idNumber)
    }

    suspend fun getUserByPrimaryRfid(rfidTag: String): User? {
        return userDao.getUserByPrimaryRfid(rfidTag)
    }

    fun addSecondaryRfidTag(userId: Long, tagId: String) {
        viewModelScope.launch {
            val tag = SecondaryRfidTag(tagId = tagId, userId = userId)
            userDao.insertSecondaryRfidTag(tag)
            loadSecondaryRfidTags(userId)
        }
    }

    fun deleteSecondaryRfidTag(tag: SecondaryRfidTag) {
        viewModelScope.launch {
            userDao.deleteSecondaryRfidTag(tag)
            loadSecondaryRfidTags(tag.userId)
        }
    }

    fun loadSecondaryRfidTags(userId: Long) {
        viewModelScope.launch {
            _secondaryRfidTags.value = userDao.getSecondaryRfidTagsForUser(userId)
        }
    }
} 