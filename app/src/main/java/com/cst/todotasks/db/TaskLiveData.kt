package com.cst.todotasks.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskLiveData : ViewModel() {
    private val _todoListData = MutableLiveData<List<Task>>()
    val todoListData: LiveData<List<Task>>
        get() = _todoListData

    private val _itemData = MutableLiveData<Task>()
    val itemData: LiveData<Task>
        get() = _itemData

    fun setTask(task: Task?) {
        _itemData.postValue(task)
    }

    fun setTasks(tasks: List<Task>?) {
        _todoListData.postValue(tasks)
    }

    fun getTasks(context: Context) {
        setTasks(Actions.getTasks(context))
    }
}