package com.cst.todotasks.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_tasks")

data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val isCompleted: Boolean = false
)