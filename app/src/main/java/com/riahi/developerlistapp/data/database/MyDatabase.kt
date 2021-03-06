package com.riahi.developerlistapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riahi.developerlistapp.data.dao.DeveloperDao
import com.riahi.developerlistapp.data.model.Developer


@Database(entities = [Developer::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun developerDao(): DeveloperDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "developers.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}