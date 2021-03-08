package com.example.mytodolist.ui.TaskList


import android.app.Application
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(val dataSource: TODOListDao, application: Application) :
    AndroidViewModel(application) {
    private lateinit var currentFiltering: TasksFilterType

    var currentIcon=MutableLiveData<TasksFilterType>()
    val _currentIcon:LiveData<TasksFilterType>
    get() = currentIcon

    private val currentFilteringLabel = MutableLiveData<String>()
    val _currentFilteringLabel: LiveData<String> = currentFilteringLabel

    private var noTaskLabel = MutableLiveData<String>()
    val _noTaskLabel: LiveData<String>
        get() = noTaskLabel

    //get all the data
    var taskData = dataSource.getAllTask()

    fun updateCompleted(isCompleted: Boolean, taskId: Long) {
        viewModelScope.launch {
            updateTaskCompleted(isCompleted, taskId)
        }

    }

    private suspend fun updateTaskCompleted(isCompleted: Boolean, taskId: Long) {
        withContext(Dispatchers.IO) {
            dataSource.updateCompletedTasks(isCompleted, taskId)
        }
    }


    fun clearCompletedTasks() {
        viewModelScope.launch {
            clearAllCompletedTasks()
        }
    }

    private suspend fun clearAllCompletedTasks() {
        withContext(Dispatchers.IO) {

            dataSource.deleteCompletedTasks()
        }
    }

    fun setFiltering(requestType: TasksFilterType) {
        currentFiltering = requestType

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        when (requestType) {
            TasksFilterType.ALL_TASKS -> {
                setFilter(
                    "All Tasks", "You have no task"
                )
            }
            TasksFilterType.ACTIVE_TASKS -> {
                setFilter(
                    "Active Task", "You have no active task"                )
            }
            TasksFilterType.COMPLETED_TASKS -> {
                setFilter(
                    "Completed Task", "You have no completed task"
                )
            }
        }
        // Refresh list
//        loadTasks(false)
    }


    val result = MutableLiveData<List<Tasks>>()
    val _result: LiveData<List<Tasks>>
        get() = result

    private fun setFilter(label: String, noTasksLabel: String) {
        currentFilteringLabel.value = label

        noTaskLabel.value = noTasksLabel

        viewModelScope.launch {
            result.value = filterItems(currentFiltering)
            Log.e(this.toString(), "Entery      22222222222222${result.value}")

        }

    }


    var tasksToShow = ArrayList<Tasks>()


    var isDataLoadError = MutableLiveData<Boolean>()
    val _isDataLoadError: LiveData<Boolean>
        get() = isDataLoadError

    fun completedTask(task: Tasks, completed: Boolean) {
        if (completed) {
            showSnackbarMessage("Task marked is completed")

        } else {
            showSnackbarMessage("Task marked is active")
        }
    }

    var snackbarMessage = MutableLiveData<String>()
    val _snackbarMessage: LiveData<String>
        get() = snackbarMessage

    private fun showSnackbarMessage(taskComplete: String) {
        snackbarMessage.value = taskComplete
    }


    private fun filterItems(filteringType: TasksFilterType): List<Tasks> {
        Log.e(this.toString(), "#########11111111111111$filteringType")
        tasksToShow.clear()
        try {
//            Transformations.map(taskData) {
            taskData.value?.forEach {
                Log.e(this.toString(), "#########$it")

                when (filteringType) {
                    TasksFilterType.ALL_TASKS -> tasksToShow.add(it)
                    TasksFilterType.ACTIVE_TASKS -> if (!it.isCompleted) {
                        tasksToShow.add(it)
                    }
                    TasksFilterType.COMPLETED_TASKS -> if (it.isCompleted) {
                        tasksToShow.add(it)
                    }

                }
                Log.e(this.toString(), "eeeeeeeeeee$tasksToShow")


//                }
            }
        } catch (io: Exception) {
            Log.e(this.toString(), "Exception")

        }
        // We filter the tasks based on the requestType

        return tasksToShow
    }
}


