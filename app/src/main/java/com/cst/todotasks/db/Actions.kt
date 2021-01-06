package com.cst.todotasks.db

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Actions {
    companion object {
        fun insert(context: Context, task: Task) {
            CoroutineScope(IO).launch {
                TaskDatabase.getDatabaseClient(context).taskDao().insert(task)
            }
        }

        fun update(context: Context, task: Task) {
            CoroutineScope(IO).launch {
                TaskDatabase.getDatabaseClient(context).taskDao().update(task)
            }
        }

        fun deleteCompletedTasks(context: Context) {
            TaskDatabase.getDatabaseClient(context).taskDao().deleteCompletedTasks()
        }

        suspend fun delete(context: Context, task: Task) {
            TaskDatabase.getDatabaseClient(context).taskDao().delete(task)
        }

        fun getTasks(context: Context): List<Task> {
            return TaskDatabase.getDatabaseClient(context).taskDao().getTasks()
        }
    }
}