package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cst.todotasks.R
import com.cst.todotasks.db.Actions
import com.cst.todotasks.db.Task
import com.cst.todotasks.db.TaskLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar.*

class AddEditFragment : Fragment(R.layout.fragment_add_edit_item) {

    private lateinit var taskView: View
    private val taskLiveData: TaskLiveData by navGraphViewModels(R.id.todo_nav)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskView = inflater.inflate(R.layout.fragment_add_edit_item, container, false)

        val saveTask = taskView.findViewById<FloatingActionButton>(R.id.save_task)
        val title = taskView.findViewById<EditText>(R.id.task_name)
        val desc = taskView.findViewById<EditText>(R.id.description)
        var newTask: Task

        taskLiveData.itemData.observe(viewLifecycleOwner, { task ->
            when (task) {
                null -> {
                    title.text = null
                    desc.text = null
                }
                else -> {
                    title.setText(task.name)
                    desc.setText(task.description)
                }
            }

            saveTask.setOnClickListener {
                when {
                    title.text.isNotEmpty() && desc.text.isNotEmpty() -> {
                        when (task) {
                            null -> {
                                newTask = Task(
                                    name = title.text.toString(),
                                    description = desc.text.toString(),
                                    isCompleted = false
                                )
                                Actions.insert(taskView.context, newTask)
                                make(taskView, getText(R.string.task_added), LENGTH_SHORT).show()
                            }
                            else -> {
                                task.name = title.text.toString()
                                task.description = desc.text.toString()
                                Actions.update(taskView.context, task)
                                make(taskView, getText(R.string.task_edited), LENGTH_SHORT).show()
                            }
                        }
                        findNavController().navigate(R.id.fragment_task_list)
                    }
                    else -> {
                        make(taskView, getText(R.string.required), LENGTH_SHORT).show()

                        return@setOnClickListener
                    }
                }
            }
        })

        return taskView
    }
}