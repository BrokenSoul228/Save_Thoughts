package com.example.data.repositories

import android.content.Context
import com.example.data.DataBase
import com.example.data.dao.ItemsDao
import com.example.data.entities.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemsRepository(context : Context) {

    val itemDao = DataBase.getDatabase(context).itemDao()

    suspend fun insertNewItem(item : Items) {
        withContext(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    suspend fun getAllUsersItem() : List<Items> {
        return withContext(Dispatchers.IO) {
            return@withContext itemDao.getAllNotes()
        }
    }

    suspend fun update(item : Items) {
        withContext(Dispatchers.IO) {
            itemDao.update(item)
        }
    }

    suspend fun getCurrentItem(id : Long) : Items {
        return withContext(Dispatchers.IO) {
            return@withContext itemDao.getCurrentItem(id)
        }
    }

    suspend fun deleteSelectedItem(id : Long) {
        withContext(Dispatchers.IO){
            itemDao.delete(id)
        }
    }
}