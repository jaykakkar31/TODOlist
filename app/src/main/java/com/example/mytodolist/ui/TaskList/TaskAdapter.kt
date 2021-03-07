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


class TaskAdapter(val Clicklistener: Clicklisteners, val listenerTask: ListenerTask) :
    ListAdapter<Tasks, TaskAdapter.ViewHolder>(TaksDiffUtilCallBack()) {


//    var data = listOf<Tasks>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }


    class ListenerTask(val listener: (id: Long, isCompleted: Boolean) -> Unit) {



        fun OnClickCheckBox(tasks: Tasks, complete: Boolean) {
            listener(tasks.taskId, complete)

        }
    }

    class ViewHolder(var binding: TaskListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title = binding.title
        var checkbox = binding.checkbox
        var taskFragment = TaskFragment()
        fun bind(item: Tasks) {
            title.text = item.title
            checkbox.isChecked=item.isCompleted
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
//                Clicklistener.onCheck(item, true)
                listenerTask.OnClickCheckBox(item, true)
                Log.e(it.context.toString(), "!!!!!!!!!!!!!!!${holder.checkbox.isChecked}")
                Toast.makeText(it.context, "Task has been marked completed", Toast.LENGTH_SHORT)
                    .show()

            } else {
                listenerTask.OnClickCheckBox(item, false)
                Toast.makeText(it.context, "Task has been active", Toast.LENGTH_SHORT).show()
            }
        }


        holder.title.setOnClickListener {
            Clicklistener.onClick(item)
//            Clicklistener.clickListener(item.taskId)
        }
        holder.bind(item)
    }


    class Clicklisteners(var clickListener: (id: Long) -> Unit) {

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


