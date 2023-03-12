package com.example.basiccontactappwithotpsender.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class SentMessageModel(
    @PrimaryKey(autoGenerate = true)
    val messageId: Long = 0L,
    val date: String,
    val messageSubject: String,
    val messageName: String,
    val messagePhone: String,
    val messageOtp: String,
)
