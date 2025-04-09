package com.example.nfcreader.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val surname: String,
    val idNumber: String,
    val phoneNumber: String,
    val primaryRfidTag: String?,
    val balance: Double = 0.0
)

@Entity(tableName = "secondary_rfid_tags")
data class SecondaryRfidTag(
    @PrimaryKey
    val tagId: String,
    val userId: Long
) 