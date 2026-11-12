package com.example.synctask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.synctask.data.Task

class TaskAdapter(
    private val onDeleteClickListener: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)
        private val textViewTaskTitle: TextView = itemView.findViewById(R.id.textViewTaskTitle)
        private val textViewTaskDescription: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.delete_icon)

        fun bind(task: Task, onDeleteClickListener: (Task) -> Unit) {
            textViewTaskTitle.text = task.title
            textViewTaskDescription.text = task.description
            checkBoxCompleted.isChecked = task.isCompleted
            deleteIcon.setOnClickListener { onDeleteClickListener(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onDeleteClickListener)
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}