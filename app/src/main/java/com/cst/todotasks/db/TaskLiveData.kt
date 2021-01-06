package com.cst.todotasks.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskLiveData : ViewModel() {
    private val _tasksLiveData = MutableLiveData<List<Task>>()
    val tasksLiveData: LiveData<List<Task>>
        get() = _tasksLiveData

    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData: LiveData<Task>
        get() = _taskLiveData

    fun postTask(task: Task?) {
        _taskLiveData.postValue(task)
    }

    fun postTasks(tasks: List<Task>?) {
        _tasksLiveData.postValue(tasks)
    }

    fun getTasks(context: Context) {
        postTasks(Actions.getTasks(context))
    }
}