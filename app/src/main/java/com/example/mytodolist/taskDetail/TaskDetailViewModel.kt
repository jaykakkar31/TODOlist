package com.example.mytodolist.taskDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.Database.TODOListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailViewModel(val dataSource: TODOListDao, val TaskId: Long=0L) : ViewModel() {

    fun getDetails() {
        viewModelScope.launch {
            //overcome error cannot set value on background thread
            var currentTask = dataSource.getTaskById(TaskId)?: return@launch
            title.value = currentTask?.title
            description.value = currentTask?.descp
            isCompleted.value = currentTask?.isCompleted        }
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

    private suspend fun getDetailsWithTaskId() {
        withContext(Dispatchers.IO){

        }


    }
}