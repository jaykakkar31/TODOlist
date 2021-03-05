package com.example.mytodolist.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.AddTaskBinding


class AddTask() : Fragment() {
    private lateinit var viewModel: addTaskViewModel
    private lateinit var binding: AddTaskBinding
    private lateinit var dataSource: TODOListDao
    private lateinit var args: AddTaskArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        dataSource = TasksDatabase.getInstance(application).TODOListDao


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        args = AddTaskArgs.fromBundle(requireArguments())

//        Log.e(this.toString(),"TITLEXXXXX$a")
        val viewModelFactory = addTaskViewModelFactory(dataSource, args.TaskId,args.title)
        viewModel = ViewModelProvider(this, viewModelFactory).get(addTaskViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task, container, false)

        binding.saveTaskFab.setOnClickListener {
            saveTask()
        }

        viewModel.getTaskWithId()

        viewModel._title.observe(viewLifecycleOwner, Observer {
            binding.addTaskTitleEditText.setText(it)
        })

        viewModel._description.observe(viewLifecycleOwner, Observer {
            binding.addTaskDescriptionEditText.setText(it)
        })
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
            if(args.TaskId!=0L){
                viewModel.updateTaskWithID(title,description)
                Toast.makeText(requireActivity(), "Task is saved", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_addTask_to_nav_tasks)


            }
            else{
                var newTask: Tasks = Tasks()
                viewModel.saveDataInDatabase(title, description, newTask)
                Toast.makeText(requireActivity(), "Task is saved", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addTask_to_nav_tasks)
            }

        }
    }


}