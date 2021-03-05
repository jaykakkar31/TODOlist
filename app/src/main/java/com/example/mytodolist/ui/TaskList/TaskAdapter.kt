package com.example.mytodolist.ui.TaskList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.databinding.TaskListBinding


class TaskAdapter(val Clicklistener: Clicklisteners) :
    ListAdapter<Tasks, TaskAdapter.ViewHolder>(TaksDiffUtilCallBack()) {
//    var data = listOf<Tasks>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    class ViewHolder(var binding: TaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        var title = binding.title
        var checkbox = binding.checkbox

        fun bind(item: Tasks) {
            title.text = item.title
        }

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item=data[position]
        val item = getItem(position)
        holder.checkbox.setOnClickListener {
            if (holder.checkbox.isChecked) {
                Log.e(it.context.toString(), "!!!!!!!!!!!!!!!${holder.checkbox.isChecked}")
                Toast.makeText(it.context, "Task has been marked completed", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(it.context, "Task has been active", Toast.LENGTH_SHORT).show()
            }
        }


        holder.title.setOnClickListener {
            Clicklistener.onClick(item)
        }
        holder.bind(item)
    }


    class Clicklisteners(val clickListener: (id: Long) -> Unit) {
        fun onClick(tasks: Tasks) {
            clickListener(tasks.taskId)
        }


    }


    class TaksDiffUtilCallBack : DiffUtil.ItemCallback<Tasks>() {
        override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem == newItem
        }

    }

}


