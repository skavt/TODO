package com.cst.todotasks.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_tasks")

data class Task(
    val name: String,
    val description: String,
    val completed: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)