package com.cst.todotasks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.Actions
import com.cst.todotasks.db.Task
import com.cst.todotasks.db.TaskDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by nikolozakhvlediani on 12/24/20.
 */
class TaskListFragment : Fragment(R.layout.fragment_task_list),
    TaskListAdapter.OnItemClickListener {

    private lateinit var taskListView: View

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskListView = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)

        val taskItem = taskListView.findViewById<RecyclerView>(R.id.task_item)
        val allTasks = taskListView.findViewById<LinearLayout>(R.id.all_task_view)
        val noTasks = taskListView.findViewById<LinearLayout>(R.id.no_tasks_view)
        val data = TaskDatabase.getDatabaseClient(taskListView.context).taskDao().getTasks()

        when {
            data.isNotEmpty() -> {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    taskItem.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TaskListAdapter(
                            Actions.getTasks(context),
                            this@TaskListFragment
                        )
                    }
                }
                allTasks.visibility = VISIBLE
                noTasks.visibility = GONE
            }
            else -> {
                allTasks.visibility = GONE
                noTasks.visibility = VISIBLE
            }
        }


        val addTask = taskListView.findViewById<FloatingActionButton>(R.id.add_task)

        addTask.setOnClickListener {
            (activity as AppCompatActivity).title = getString(R.string.new_task)
            findNavController().navigate(R.id.action_taskList_to_addTask)
        }
        return taskListView
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {
                // TODO თქვენი კოდი
                true
            }
            R.id.menu_filter -> {
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

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.active -> {
                        // TODO თქვენი კოდი
                    }
                    R.id.completed -> {
                        // TODO თქვენი კოდი
                    }
                    else -> {
                        // TODO თქვენი კოდი
                    }
                }
                true
            }
            show()
        }
    }

    companion object {

        fun createInstance() = TaskListFragment()

    }

    override fun onItemClick(task: Task) {
        make(taskListView, getText(R.string.completed), LENGTH_SHORT).show()
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        task.isCompleted = isChecked
        Actions.update(taskListView.context, task)
        when {
            isChecked -> make(taskListView, getText(R.string.completed), LENGTH_SHORT).show()
            else -> make(taskListView, getText(R.string.active), LENGTH_SHORT).show()
        }
    }

}