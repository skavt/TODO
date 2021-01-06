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

    private var task: Task? = null
    private val taskLiveData: TaskLiveData by navGraphViewModels(R.id.todo_nav)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_edit_item, container, false)
        val saveTask = view.findViewById<FloatingActionButton>(R.id.save_task)
        val title = view.findViewById<EditText>(R.id.task_name)
        val desc = view.findViewById<EditText>(R.id.description)

        taskLiveData.taskLiveData.observe(viewLifecycleOwner, {
            task = it
            when (task) {
                null -> {
                    title.text = null
                    desc.text = null
                }
                else -> {
                    title.setText(it.name)
                    desc.setText(it.description)
                }
            }
        })

        saveTask.setOnClickListener {
            when {
                title.text.isNotEmpty() && desc.text.isNotEmpty() -> {
                    when (task) {
                        null -> {
                            task = Task(
                                name = title.text.toString(),
                                description = desc.text.toString(),
                                isCompleted = false
                            )
                            context?.let { context ->
                                Actions.insert(context, task!!)
                                make(it, getText(R.string.task_added), LENGTH_SHORT).show()
                            }
                        }
                        else -> {
                            task!!.name = title.text.toString()
                            task!!.description = desc.text.toString()
                            context?.let { context ->
                                Actions.update(context, task!!)
                                make(it, getText(R.string.task_edited), LENGTH_SHORT).show()
                            }
                        }
                    }
                    findNavController().navigate(R.id.fragment_task_list)
                }
                else -> {
                    make(it, getText(R.string.required), LENGTH_SHORT).show()

                    return@setOnClickListener
                }
            }
        }

        return view
    }
}