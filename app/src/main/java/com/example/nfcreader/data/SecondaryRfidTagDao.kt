package com.example.nfcreader.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SecondaryRfidTagDao {
    @Query("SELECT * FROM secondary_rfid_tags WHERE userId = :userId")
    suspend fun getSecondaryRfidTagsForUser(userId: Long): List<SecondaryRfidTag>

    @Query("SELECT * FROM secondary_rfid_tags WHERE tagId = :tagId")
    suspend fun getSecondaryRfidTagByTagId(tagId: String): SecondaryRfidTag?

    @Insert
    suspend fun insertSecondaryRfidTag(tag: SecondaryRfidTag)

    @Delete
    suspend fun deleteSecondaryRfidTag(tag: SecondaryRfidTag)
} 