package com.example.mytodolist.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.AddTaskBinding


class AddTask() : Fragment() {
    private lateinit var viewModel: addTaskViewModel
    private lateinit var binding: AddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = TasksDatabase.getInstance(application).TODOListDao
        val viewModelFactory = addTaskViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(addTaskViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task, container, false)

        binding.saveTaskFab.setOnClickListener {
            saveTask()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveTask() {
        var title = binding.addTaskTitleEditText.text.toString()
        var description = binding.addTaskDescriptionEditText.text.toString()
        var isCompleted = false

        if (title.isEmpty()) {
            Toast.makeText(requireActivity(), "Title can not be empty", Toast.LENGTH_SHORT).show()
        } else {
            var newTask: Tasks = Tasks()
            viewModel.saveDataInDatabase(title, description, newTask)
            Toast.makeText(requireActivity(),"Task is saved",Toast.LENGTH_SHORT).show()
        }
    }



}