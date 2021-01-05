package com.cst.todotasks.db

import android.content.Context
import androidx.room.*

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabaseClient(context: Context): TaskDatabase {
            when {
                INSTANCE != null -> return INSTANCE!!
                else -> synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()

                    return INSTANCE!!
                }
            }

        }
    }
}