package com.cst.todotasks.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.db.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskListAdapter(
    private val tasks: List<Task>,
    private val listener: OnItemClickListener
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

        fun setContent(task: Task) {
            with(task) {
                itemView.text_view_name.text = name
                when {
                    task.isCompleted -> {
                        itemView.check_box_completed.isChecked = true
                        itemView.text_view_name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
                itemView.check_box_completed.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                    when {
                        isChecked -> itemView.text_view_name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        else -> itemView.text_view_name.paintFlags = Paint.ANTI_ALIAS_FLAG
                    }

                    listener.onCheckBoxClick(task, isChecked)
                }

                itemView.text_view_name.setOnClickListener {
                    listener.onItemClick(task)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }
}