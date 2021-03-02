package com.example.mytodolist.Database

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder

@Entity(tableName = "Tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    var entryId:Long=0L,
    @ColumnInfo(name = "Title")
    var title:String?=null,
    @ColumnInfo(name = "Description")
    var descp:String?=null,
    @ColumnInfo(name = "completed")
    var isCompleted:Boolean=false
)