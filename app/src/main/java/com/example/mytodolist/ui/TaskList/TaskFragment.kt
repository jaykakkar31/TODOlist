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
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.google.android.material.snackbar.Snackbar

class TaskFragment : Fragment() {
    private lateinit var viewModel: TaskViewModel
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
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(TaskFragmentDirections.actionTaskListFragmentToAddTask(0L,"Add Task"))
        }
        adapter=TaskAdapter(TaskAdapter.Clicklisteners{
            findNavController().navigate(TaskFragmentDirections.actionNavTasksToTaskDetails(it))
        })





        viewModel.taskData.observe(viewLifecycleOwner, Observer {
            Log.e(this.toString(),"##############$it")
//            adapter.data = it
            adapter.submitList(it)
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
                        R.id.active -> TasksFilterType.ACTIVE_TASKS
                        R.id.completed -> TasksFilterType.COMPLETED_TASKS
                        else -> TasksFilterType.ALL_TASKS
                    }

                )


                true
            }
            show()

        }
    }
}