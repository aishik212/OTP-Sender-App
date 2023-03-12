package com.example.basiccontactappwithotpsender.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.model.SentMessageModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MessageDBTest : TestCase() {


    private lateinit var db: MessageDB
    private lateinit var dao: MessageDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, MessageDB::class.java).allowMainThreadQueries()
            .build()
        dao = db.messageDao
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun checkIfInsertedSuccessfully() = runBlocking {
        val language = SentMessageModel(
            messageId = 11,
            date = "${System.currentTimeMillis()}",
            messageName = "AAA",
            messageOtp = "Your OTP is 124545",
            messagePhone = "8787888787",
            messageSubject = "adasdads"
        )
        dao.insert(language)
        val languages: MutableList<SentMessageModel> = dao.getAllMessage()
        assertThat(languages.contains(language), equalTo(true))
    }

    @Test
    fun checkIfDuplicateNotInserted() = runBlocking {
        val language = SentMessageModel(
            messageId = 11,
            date = "${System.currentTimeMillis()}",
            messageName = "AAA",
            messageOtp = "Your OTP is 124545",
            messagePhone = "8787888787",
            messageSubject = "adasdads"
        )
        dao.insert(language)
        val firstSize = dao.getAllMessage().size
        dao.insert(language)
        val secondSize = dao.getAllMessage().size

        assertThat(firstSize == secondSize, equalTo(true))
    }
}