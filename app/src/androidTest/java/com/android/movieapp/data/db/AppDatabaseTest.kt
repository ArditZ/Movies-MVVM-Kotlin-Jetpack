package com.android.movieapp.data.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before

abstract class AppDatabaseTest {

    private lateinit var _appDatabase: AppDatabase
    val db: AppDatabase
        get() = _appDatabase

    @Before
    fun initDb() {
        _appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        db.close()
    }
}