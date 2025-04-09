package com.example.nfcreader.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE idNumber = :idNumber")
    suspend fun getUserByIdNumber(idNumber: String): User?

    @Query("SELECT * FROM users WHERE primaryRfidTag = :rfidTag")
    suspend fun getUserByPrimaryRfid(rfidTag: String): User?

    @Query("SELECT * FROM secondary_rfid_tags WHERE tagId = :rfidTag")
    suspend fun getSecondaryRfidTag(rfidTag: String): SecondaryRfidTag?

    @Query("SELECT * FROM secondary_rfid_tags WHERE userId = :userId")
    suspend fun getSecondaryRfidTagsForUser(userId: Long): List<SecondaryRfidTag>

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Insert
    suspend fun insertSecondaryRfidTag(tag: SecondaryRfidTag)

    @Delete
    suspend fun deleteSecondaryRfidTag(tag: SecondaryRfidTag)
} 