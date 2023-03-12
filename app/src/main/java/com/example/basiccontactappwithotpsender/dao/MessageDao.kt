package com.example.basiccontactappwithotpsender.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basiccontactappwithotpsender.model.SentMessageModel

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: SentMessageModel): Long

    @Query("Select * from message")
    fun getAllMessage(): MutableList<SentMessageModel>

}
