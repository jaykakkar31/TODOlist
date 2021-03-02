package com.example.mytodolist.ui.TaskList

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.databinding.TaskListBinding

class TaskListFragment : Fragment() {

    private lateinit var taskListViewModel: TaskListViewModel
    private lateinit var binding:FragmentTaskBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        taskListViewModel =
                ViewModelProvider(this).get(TaskListViewModel::class.java)
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_task,container,false)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addTask)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }
}