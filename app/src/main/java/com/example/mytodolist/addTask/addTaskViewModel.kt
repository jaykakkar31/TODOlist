package com.example.mytodolist.addTask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.Database.TODOListDao
import com.example.mytodolist.Database.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class addTaskViewModel(val dataSource: TODOListDao, application: Application) : ViewModel() {
    fun saveDataInDatabase(title: String, description: String, Tasks: Tasks) {
        viewModelScope.launch {
            Tasks.title=title.toString()
            Tasks.descp=description.toString()
            saveTask(Tasks)
        }
    }

    suspend fun saveTask(tasks: Tasks) {
        withContext(Dispatchers.IO){
            dataSource.insertTask(tasks)
        }
    }
}