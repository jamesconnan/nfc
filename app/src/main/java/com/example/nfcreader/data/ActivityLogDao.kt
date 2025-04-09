package com.example.nfcreader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityLogDao {
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC")
    fun getAllActivityLogs(): Flow<List<ActivityLog>>

    @Query("SELECT * FROM activity_logs WHERE user_id = :userId ORDER BY timestamp DESC")
    fun getActivityLogsByUser(userId: String): Flow<List<ActivityLog>>

    @Insert
    suspend fun insertActivityLog(activityLog: ActivityLog)
} 