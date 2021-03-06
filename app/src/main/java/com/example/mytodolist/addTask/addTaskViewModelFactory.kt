package com.example.mytodolist.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.Database.TODOListDao

class addTaskViewModelFactory(
    private val dataSource: TODOListDao,
    private val TaskId: Long,
   private val titleLabel: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(addTaskViewModel::class.java)) {
            return addTaskViewModel(dataSource, TaskId,titleLabel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

