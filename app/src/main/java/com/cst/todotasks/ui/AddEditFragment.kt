package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cst.todotasks.R
import com.cst.todotasks.db.Task
import com.cst.todotasks.db.TaskDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditFragment : Fragment(R.layout.fragment_add_edit_item) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_edit_item, container, false)

        val saveTask = view.findViewById<FloatingActionButton>(R.id.save_task)
        val title = view.findViewById<EditText>(R.id.task_name)
        val description = view.findViewById<EditText>(R.id.description)

        saveTask.setOnClickListener {
            if (title.text.isEmpty() || title.text.isBlank()) {
                Snackbar.make(it, getText(R.string.required), Snackbar.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                TaskDatabase.getDatabaseClient(view.context).taskDao().insert(
                    Task(
                        name = title.text.toString(),
                        description = description.text.toString(),
                        isCompleted = false
                    )
                )
            }
            (activity as AppCompatActivity).title = getString(R.string.todo)
            findNavController().navigate(R.id.action_addTask_to_taskList)
        }

        return view
    }
}