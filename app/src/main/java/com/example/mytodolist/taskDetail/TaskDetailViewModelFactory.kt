package com.example.mytodolist.taskDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.Database.TODOListDao

class TaskDetailViewModelFactory(private val dataSource: TODOListDao,
                                 private val TaskId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            return TaskDetailViewModel(dataSource, TaskId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

