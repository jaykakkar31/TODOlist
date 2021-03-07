package com.example.mytodolist.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TODOListDao {

    @Query("SELECT * FROM Tasks")
    fun getAllTask(): LiveData<List<Tasks>>

    @Query("SELECT * FROM Tasks WHERE taskId=:TaskId")
    suspend fun getTaskById(TaskId: Long): Tasks?

    @Query("UPDATE Tasks SET completed = :completed WHERE taskId = :taskId")
    suspend fun updateCompleted(taskId: Long, completed: Boolean)

    @Query("DELETE FROM Tasks WHERE taskId = :taskId")
    suspend fun deleteTaskById(taskId: Long): Int

    @Query("DELETE FROM Tasks WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int

    @Query("DELETE FROM Tasks")
    suspend fun deleteTasks()

    @Insert
    suspend fun insertTask(task: Tasks)

    @Query("UPDATE Tasks SET Title =:titleTask,Description = :taskDescription WHERE taskId=:mtaskId")
    suspend fun updateTaskWithId(

        titleTask: String,
        taskDescription: String,
        mtaskId: Long

    )
    @Query("UPDATE Tasks SET completed=:isCompleted WHERE taskId=:taskId")
    suspend fun updateCompletedTasks(isCompleted:Boolean,taskId: Long)
}