package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cst.todotasks.R

class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        (activity as AppCompatActivity).title = getString(R.string.details)

        return view
    }
}