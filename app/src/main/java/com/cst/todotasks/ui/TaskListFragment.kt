package com.cst.todotasks.ui

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.Actions
import com.cst.todotasks.db.Task
import com.cst.todotasks.db.TaskLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar.*

/**
 * Created by nikolozakhvlediani on 12/24/20.
 */
class TaskListFragment : Fragment(R.layout.fragment_task_list),
    TaskListAdapter.OnItemClickListener {

    private lateinit var taskListView: View
    private lateinit var listHeader: TextView
    private lateinit var allTasks: LinearLayout
    private lateinit var noTasks: LinearLayout
    private lateinit var data: List<Task>
    private var isFiltered: Boolean = false
    private val taskLiveData: TaskLiveData by navGraphViewModels(R.id.todo_nav)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskListView = inflater.inflate(R.layout.fragment_task_list, container, false)
        (activity as AppCompatActivity).title = getString(R.string.app_name)
        setHasOptionsMenu(true)

        val taskItem = taskListView.findViewById<RecyclerView>(R.id.task_item)
        val addTask = taskListView.findViewById<FloatingActionButton>(R.id.add_task)

        allTasks = taskListView.findViewById(R.id.all_task_view)
        noTasks = taskListView.findViewById(R.id.no_tasks_view)
        listHeader = taskListView.findViewById(R.id.all_tasks)

        taskLiveData.todoListData.observe(viewLifecycleOwner, { task ->
            when {
                task.isNotEmpty() -> {
                    taskItem.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TaskListAdapter(task, this@TaskListFragment)
                    }
                    when {
                        !isFiltered -> data = task
                    }
                    isFiltered = false
                    allTasks.visibility = VISIBLE
                    noTasks.visibility = GONE
                }
                else -> {
                    allTasks.visibility = GONE
                    noTasks.visibility = VISIBLE
                }
            }
        })

        taskLiveData.getTasks(taskListView.context)

        addTask.setOnClickListener {
            taskLiveData.saveTask(null)
            (activity as AppCompatActivity).title = getString(R.string.app_name_new_task)
            findNavController().navigate(R.id.action_taskList_to_addTask)
        }

        return taskListView
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {
                Actions.deleteCompletedTasks(taskListView.context)
                taskLiveData.getTasks(taskListView.context)
                make(taskListView, getText(R.string.delete_completed_tasks), LENGTH_SHORT).show()
                true
            }
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            R.id.delete_task -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.active -> {
                        filterTasks(
                            getText(R.string.active_tasks) as String,
                            data.filter { !it.isCompleted })
                    }
                    R.id.completed -> {
                        filterTasks(
                            getText(R.string.completed_tasks) as String,
                            data.filter { it.isCompleted })
                    }
                    else -> {
                        filterTasks(getText(R.string.all_tasks) as String, data)
                    }
                }
                true
            }
            show()
        }
    }

    private fun filterTasks(text: String, filteredData: List<Task>) {
        listHeader.text = text
        isFiltered = true
        taskLiveData.saveTasks(filteredData)
    }

    override fun onItemClick(task: Task) {
        taskLiveData.saveTask(task)
        taskListView.findNavController().navigate(R.id.action_taskList_to_taskDetails)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        task.isCompleted = isChecked
        Actions.update(taskListView.context, task)
        when {
            isChecked -> make(taskListView, getText(R.string.snack_completed), LENGTH_SHORT).show()
            else -> make(taskListView, getText(R.string.snack_active), LENGTH_SHORT).show()
        }
    }

}