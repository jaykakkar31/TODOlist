package com.example.mytodolist.ui.TaskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.databinding.TaskListBinding

class TaskAdapter(val ClickListener:Clicklisteners) : ListAdapter<Tasks, TaskAdapter.ViewHolder>(TaksDiffUtilCallBack()) {
//    var data = listOf<Tasks>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    class ViewHolder(var binding: TaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        var title = binding.title
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
        holder.title.setOnClickListener {
            ClickListener.clickListener(item.entryId)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return 0
    }
    class Clicklisteners(val clickListener: (id:Long) -> Unit) {

        fun onClick(task: Tasks) {
            clickListener(task.entryId)
        }


    }
    class TaksDiffUtilCallBack : DiffUtil.ItemCallback<Tasks>() {
        override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem.entryId==newItem.entryId
        }

        override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem==newItem
        }

    }

}


