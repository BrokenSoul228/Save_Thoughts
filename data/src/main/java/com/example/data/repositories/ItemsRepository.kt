package com.example.data.repositories

import android.content.Context
import android.util.Log
import com.example.data.DataBase
import com.example.data.entities.Items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ItemsRepository(context : Context) {

    private val itemDao = DataBase.getDatabase(context).itemDao()

    fun getAllUsersItem() : Flow<MutableList<Items>> = itemDao.getAllNotes()

    suspend fun insertNewItem(item : Items) = itemDao.insert(item)

    suspend fun update(item : Items) = itemDao.update(item)

    suspend fun deleteSelectedItem(id : Long) = itemDao.delete(id)

    suspend fun updateTagColor(id : Long, color : String) = itemDao.updateTag(id, color)

    fun groupByTags(tag: String): Flow<MutableList<Items>> = itemDao.groupByTags("%$tag%")

}