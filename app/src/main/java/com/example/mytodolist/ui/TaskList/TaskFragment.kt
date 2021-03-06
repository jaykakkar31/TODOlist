package com.example.mytodolist.ui.TaskList

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TaskFragment : Fragment() {
    lateinit var viewModel: TaskViewModel
    private lateinit var binding: FragmentTaskBinding
    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = TasksDatabase.getInstance(application).TODOListDao
        val viewModelFactory = TasksViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                TaskFragmentDirections.actionTaskListFragmentToAddTask(
                    0L,
                    "Add Task"
                )
            )
        }
        adapter = TaskAdapter(TaskAdapter.Clicklisteners{
            findNavController().navigate(TaskFragmentDirections.actionNavTasksToTaskDetails(it))
        },TaskAdapter.ListenerTask{id, isCompleted ->
            viewModel.updateCompleted(isCompleted,id)
            adapter.notifyDataSetChanged()
        })




        viewModel.taskData.observe(viewLifecycleOwner, Observer {
            Log.e(this.toString(), "##############$it")
//            adapter.data = it
//            displayTask(it)
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            if(it.isEmpty()){

                displayTask()

            }

        })

        viewModel._snackbarMessage.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })



        binding.recycler.adapter = adapter
        viewModel._currentFilteringLabel.observe(viewLifecycleOwner, Observer {
            binding.tasksType.text = it

        })
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        return binding.root
    }

    private  fun displayTask() {
        binding.tasksType.visibility=View.INVISIBLE
        binding.noTasksIcon.visibility=View.VISIBLE
        binding.noTasksText.visibility=View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.clear_completed -> {
                viewModel.clearCompletedTasks()
                adapter.notifyDataSetChanged()
                true
            }
            R.id.filter -> {
                showFilteringPopUpMenu()
                true
            }
//            R.id.refresh -> {
//                viewModel.loadTasks(true)
//                true
//            }
            else -> false
        }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.filter) ?: return
        PopupMenu(activity, view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)
            Log.e(this.toString(), "Entery      33333333333")
            setOnMenuItemClickListener {
                viewModel.setFiltering(
                    when (it.itemId) {
                        R.id.active -> {TasksFilterType.ACTIVE_TASKS

                        }
                        R.id.completed -> TasksFilterType.COMPLETED_TASKS
                        else -> TasksFilterType.ALL_TASKS
                    }
                )
                viewModel._result.observe(viewLifecycleOwner, Observer { it ->
                    Log.e(this.toString(),"MMMMMMMMMMMM$it")
                    if(it.isEmpty()){
                        viewModel._noTaskLabel.observe(viewLifecycleOwner, Observer { notask->
                            binding.noTasksText.text=notask
                            binding.noTasksText.visibility=View.VISIBLE
                            binding.noTasksIcon.visibility=View.VISIBLE
                            binding.tasksType.visibility=View.INVISIBLE

                        })

                    }
                    else{
                        binding.noTasksText.visibility=View.GONE
                        binding.noTasksIcon.visibility=View.GONE
                        binding.tasksType.visibility=View.VISIBLE

                    }
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()

                })


                true
            }
            show()

        }
    }
}