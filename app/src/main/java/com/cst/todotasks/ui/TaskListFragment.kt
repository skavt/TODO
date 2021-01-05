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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.TaskDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by nikolozakhvlediani on 12/24/20.
 */
class TaskListFragment : Fragment(R.layout.fragment_task_list) {


    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)

        val taskItem = view.findViewById<RecyclerView>(R.id.task_item)
        val allTasks = view.findViewById<LinearLayout>(R.id.all_task_view)
        val noTasks = view.findViewById<LinearLayout>(R.id.no_tasks_view)
        val data = TaskDatabase.getDatabaseClient(view.context).taskDao().getTasks()

        if (data.isNotEmpty()) {
            taskItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    TaskListAdapter(TaskDatabase.getDatabaseClient(context).taskDao().getTasks())

            }
            allTasks.visibility = VISIBLE
            noTasks.visibility = GONE
        }


        val addTask = view.findViewById<FloatingActionButton>(R.id.add_task)

        addTask.setOnClickListener {
            (activity as AppCompatActivity).title = getString(R.string.new_task)
            findNavController().navigate(R.id.action_taskList_to_addTask)
        }
        return view
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

}