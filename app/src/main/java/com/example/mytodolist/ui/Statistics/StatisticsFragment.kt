package com.example.mytodolist.ui.Statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import com.example.mytodolist.Database.TasksDatabase
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var dataSource: TODOListDao
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
            ViewModelProvider(this).get(StatisticsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)

//        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            Log.e(this.toString(), "1111111111$it")
            StatTask(it)
        })
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun StatTask(it: List<Tasks>?) {
        var totalNumberOfTasks: Float = 0f

        var totalCompletedTask: Float = 0f

        if (it != null) {
            for (i in it) {
                totalNumberOfTasks++
                if (i.isCompleted) {
                    totalCompletedTask++
                }

            }
            Log.e(this.toString(), "ggggggggggg$totalCompletedTask")
            Log.e(this.toString(), "hhhhhhhhhhh$totalNumberOfTasks")

            val percentCompleted: Float = (totalCompletedTask / totalNumberOfTasks) * 100
            val percentActive = (100 - percentCompleted).toFloat()
            Log.e(this.toString(), "iiiiiiiiiiii${(1 / 2).toFloat()}")

            binding.completedPercentTasks.setText("$percentCompleted%")
            binding.activePercentTask.text = "$percentActive%"
        }
        if (it?.isEmpty()==true) {
            binding.activePercentTask.visibility = View.GONE
            binding.completedPercentTasks.visibility = View.GONE
            binding.completedTasks.visibility = View.GONE
            binding.activeTask.visibility = View.GONE
            binding.notask.visibility = View.VISIBLE
            binding.notask.text = "No task is available at present"
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        dataSource = TasksDatabase.getInstance(application).TODOListDao
        val viewModelFactory = StatisticsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StatisticsViewModel::class.java)
    }


}