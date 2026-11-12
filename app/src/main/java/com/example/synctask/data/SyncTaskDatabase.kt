package com.example.synctask.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class, Category::class, Project::class, User::class], version = 3, exportSchema = false)
abstract class SyncTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun projectDao(): ProjectDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: SyncTaskDatabase? = null

        fun getDatabase(context: Context): SyncTaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SyncTaskDatabase::class.java,
                    "synctask_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}