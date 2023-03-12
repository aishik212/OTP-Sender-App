package com.example.basiccontactappwithotpsender.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.model.SentMessageModel

@Database(entities = [SentMessageModel::class], version = 2, exportSchema = false)
abstract class MessageDB : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract val messageDao: MessageDao

    companion object {

        @Volatile
        private var INSTANCE: MessageDB? = null

        fun getInstance(context: Context): MessageDB {


            synchronized(this) {


                var instance = INSTANCE


                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MessageDB::class.java,
                        "sleep_history_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}