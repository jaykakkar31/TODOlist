package com.example.mytodolist.taskDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskDetailsBinding


class TaskDetails : Fragment() {
    private lateinit var binding: FragmentTaskDetailsBinding
    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var dataSource: TODOListDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        dataSource = TasksDatabase.getInstance(application).TODOListDao

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val args = TaskDetailsArgs.fromBundle(arguments!!)

        val viewModelFactory = TaskDetailViewModelFactory(dataSource, args.TaskId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskDetailViewModel::class.java)

        Log.e(this.toString(),"@@@@@@@@@@@@@@${args.TaskId}")
        viewModel.getDetails()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        binding.lifecycleOwner = this

        viewModel._description.observe(viewLifecycleOwner, Observer {
            Log.e(this.toString(), "!!!!!!!!!!$it")
            binding.description.text = it
        })

        viewModel._title.observe(viewLifecycleOwner, Observer {
            binding.title.text = it
        })
        viewModel._isCompleted.observe(viewLifecycleOwner, Observer {
            binding.checkbox.isChecked = it


        })

        binding.fabEdit.setOnClickListener {
            findNavController().navigate(TaskDetailsDirections.actionTaskDetailsToAddTask(args.TaskId,"Edit Task"))
        }


        return binding.root
    }


}