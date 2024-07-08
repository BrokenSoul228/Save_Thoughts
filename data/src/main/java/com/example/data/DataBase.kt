package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.ItemsDao
import com.example.data.entities.Items

@Database(entities = [Items::class], version = 4, exportSchema = false)
abstract class DataBase : RoomDatabase(){

    abstract fun itemDao(): ItemsDao

    companion object {
        @Volatile
        private var Instance: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DataBase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}