package com.example.mytodolist.ui.Statistics

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mytodolist.Database.TODOListDao

class StatisticsViewModel(val dataSource: TODOListDao, application: Application) : ViewModel() {

    var data = dataSource.getAllTask()


}