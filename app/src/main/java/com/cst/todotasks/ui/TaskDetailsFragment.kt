package com.cst.todotasks.ui

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.cst.todotasks.R
import com.cst.todotasks.db.Actions
import com.cst.todotasks.db.Task
import com.cst.todotasks.db.TaskLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar.*

class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {

    private lateinit var taskView: View
    private val taskLiveData: TaskLiveData by navGraphViewModels(R.id.todo_nav)
    private var selectedTask: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskView = inflater.inflate(R.layout.fragment_task_details, container, false)
        (activity as AppCompatActivity).title = getString(R.string.app_name_details)
        setHasOptionsMenu(true)

        val title = taskView.findViewById<TextView>(R.id.details_name)
        val checkbox = taskView.findViewById<CheckBox>(R.id.details_checkbox)
        val description = taskView.findViewById<TextView>(R.id.details_description)
        val editTask = taskView.findViewById<FloatingActionButton>(R.id.edit_task)

        taskLiveData.itemData.observe(viewLifecycleOwner, {
            selectedTask = it
            title.text = it.name
            checkbox.isChecked = it.isCompleted
            description.text = it.description

            checkbox.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                it.isCompleted = checkbox.isChecked
                Actions.update(taskView.context, it)
                when {
                    isChecked -> make(taskView, getText(R.string.snack_completed), LENGTH_SHORT).show()
                    else -> make(taskView, getText(R.string.snack_active), LENGTH_SHORT).show()
                }
            }

            editTask.setOnClickListener { item ->
                (activity as AppCompatActivity).title = getString(R.string.app_name_edit_task)
                it.let { task -> taskLiveData.saveTask(task) }
                item.findNavController().navigate(R.id.fragment_add_edit_item)
            }
        })

        return taskView
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.delete_task -> {
                context?.let {
                    selectedTask?.let { task -> Actions.delete(it, task) }
                    taskLiveData.getTasks(it)
                    make(taskView, getText(R.string.task_deleted), LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fragment_task_list)
                }
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_tasks, menu)
    }
}