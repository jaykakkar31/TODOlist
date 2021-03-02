package com.example.mytodolist.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TasksDatabase: RoomDatabase() {
    abstract val contactsDatabaseDao:TODOListDao
    companion object{
        //volatile means value is not cached and changes will be used by all
        @Volatile
        private var INSTANCE: TasksDatabase? =null
        fun getInstance(context: Context): TasksDatabase {
            synchronized(this){
                var instance= INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "Task_Database"
                    )

                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}