package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddEditFragment : Fragment(R.layout.fragment_add_edit_item) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)

        val saveTask = view.findViewById<FloatingActionButton>(R.id.save_task)

        saveTask.setOnClickListener {
            (activity as AppCompatActivity).title = getString(R.string.todo)
            // TODO room
            findNavController().navigate(R.id.action_addTask_to_taskList)
        }

        return view
    }
}