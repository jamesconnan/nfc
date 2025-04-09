package com.example.nfcreader.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.Date
import java.util.UUID

@Entity(tableName = "activity_logs")
data class ActivityLog(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    
    @ColumnInfo(name = "user_id")
    val userId: String?,
    
    @ColumnInfo(name = "action")
    val action: String,
    
    @ColumnInfo(name = "details")
    val details: String,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Date = Date()
) 