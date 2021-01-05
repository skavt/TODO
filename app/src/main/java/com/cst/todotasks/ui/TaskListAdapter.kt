package com.cst.todotasks.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskListAdapter(
    private val tasks: List<Task>,
) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setContent(cast: Task) {
            with(cast) {
                itemView.text_view_name.text = name
            }
        }
    }
}