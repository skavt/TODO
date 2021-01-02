package com.cst.todotasks.db

import androidx.room.*
import com.cst.todotasks.ui.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase {
    abstract fun taskDao(): TaskDao
}