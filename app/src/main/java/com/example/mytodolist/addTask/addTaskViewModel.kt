package com.example.mytodolist.addTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class addTaskViewModel(val dataSource: TODOListDao, val TaskId: Long, titleLabel: String) :
    ViewModel() {
    fun saveDataInDatabase(title: String, description: String, Tasks: Tasks) {
        viewModelScope.launch {
            Tasks.title = title.toString()
            Tasks.descp = description.toString()
            saveTask(Tasks)
        }
    }

    var title = MutableLiveData<String>()
    val _title: LiveData<String>
        get() = title

    var description = MutableLiveData<String>()
    val _description: LiveData<String>
        get() = description
    var isCompleted = MutableLiveData<Boolean>()
    val _isCompleted: LiveData<Boolean>
        get() = isCompleted

    fun getTaskWithId() {
        viewModelScope.launch {
            var currentTask = dataSource.getTaskById(TaskId) ?: return@launch
            title.value = currentTask?.title
            description.value = currentTask?.descp
            isCompleted.value = currentTask?.isCompleted
        }
    }

    suspend fun saveTask(tasks: Tasks) {
        withContext(Dispatchers.IO) {
            dataSource.insertTask(tasks)
        }
    }

    fun updateTaskWithID(title: String, description: String) {
        viewModelScope.launch {
            updateTask(title, description)
        }

    }

    private suspend fun updateTask(title: String, description: String) {
        withContext(Dispatchers.IO) {
            dataSource.updateTaskWithId(title,description,TaskId)
        }
    }
}