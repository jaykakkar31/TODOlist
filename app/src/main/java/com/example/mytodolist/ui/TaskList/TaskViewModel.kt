package com.example.mytodolist.ui.TaskList

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(val dataSource: TODOListDao, application: Application) :
    AndroidViewModel(application) {
    private lateinit var currentFiltering: TasksFilterType


    private val currentFilteringLabel = MutableLiveData<String>()
    val _currentFilteringLabel: LiveData<String> = currentFilteringLabel

    private var noTaskLabel = MutableLiveData<String>()
    val _noTaskLabel: LiveData<String>
        get() = noTaskLabel

    var taskData = dataSource.getAllTask()


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
                    "Active Task", "You have no active task"
                )
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


    }


    fun filterTasks() {
        Log.e(this.toString(), "Entery      22222222222222")

        viewModelScope.launch {
//            result.value = filterItems(currentFiltering)
        }

    }

    var tasksToShow = ArrayList<Tasks>()

    var isDataLoadError = MutableLiveData<Boolean>()
    val _isDataLoadError: LiveData<Boolean>
        get() = isDataLoadError

    fun completedTask(task:Tasks,completed:Boolean){
        if(completed){
            showSnackbarMessage("Task marked is completed")

        }
        else{
            showSnackbarMessage("Task marked is active")
        }
    }
    var snackbarMessage=MutableLiveData<String>()
    val _snackbarMessage:LiveData<String>
    get() = snackbarMessage

    private fun showSnackbarMessage(taskComplete: String) {
        snackbarMessage.value=taskComplete
    }


//    private fun filterItems(filteringType: TasksFilterType): List<Tasks> {
//        Log.e(this.toString(), "#########11111111111111$filteringType")
//        try {
//            Transformations.map(taskData) {
//                Log.e(this.toString(), "#########$it")
//                for (task in it) {
//                    when (filteringType) {
//                        TasksFilterType.ALL_TASKS -> tasksToShow.add(task)
//                        TasksFilterType.ACTIVE_TASKS -> if (!task.isCompleted) {
//                            tasksToShow.add(task)
//                        }
//                        TasksFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
//                            tasksToShow.add(task)
//                        }
//                    }
//                }
//            }
//        } catch (io: Exception) {
//            Log.e(this.toString(), "Exception")
//
//        }
//        // We filter the tasks based on the requestType
//
//        return tasksToShow
//    }
}


