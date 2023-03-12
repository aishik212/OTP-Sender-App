package com.example.basiccontactappwithotpsender.modules

import android.content.Context
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.db.MessageDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    //Initialize the Database Here to inject it wherever needed
    @Provides
    fun provideMessageDao(@ApplicationContext appContext: Context): MessageDao {
        return MessageDB.getInstance(appContext).messageDao
    }

}