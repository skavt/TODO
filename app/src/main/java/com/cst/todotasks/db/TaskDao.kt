package com.cst.todotasks.db

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM todo_tasks")
    fun getTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM todo_tasks WHERE is_completed = 1")
    fun deleteCompletedTasks()
}