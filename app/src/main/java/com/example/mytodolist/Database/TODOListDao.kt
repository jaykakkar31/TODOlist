package com.example.mytodolist.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TODOListDao {

    @Query("SELECT * FROM Tasks")
    suspend fun getAllTask():LiveData<List<Tasks>>

    @Query("SELECT *FROM Tasks WHERE entryId=:TaskId")
    suspend fun getTaskById(TaskId:Long):LiveData<Tasks>

    @Query("UPDATE Tasks SET completed = :completed WHERE entryId = :taskId")
    suspend fun updateCompleted(taskId: Long, completed: Boolean)

    @Query("DELETE FROM Tasks WHERE entryId = :taskId")
    suspend fun deleteTaskById(taskId: Long): Int

    @Query("DELETE FROM Tasks WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int

    @Query("DELETE FROM Tasks")
    suspend fun deleteTasks()

}