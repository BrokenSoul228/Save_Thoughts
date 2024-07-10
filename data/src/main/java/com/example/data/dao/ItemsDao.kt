package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entities.Items
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item : Items)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item : Items)

    @Query("DELETE FROM items WHERE id =:id")
    suspend fun delete(id : Long)

    @Query("SELECT * from items WHERE id = :id")
    fun getCurrentItem(id : Long) : Items

    @Query("SELECT * from items")
    fun getAllNotes() : Flow<MutableList<Items>>

    @Query("UPDATE ITEMS SET tags = :color WHERE id = :id")
    suspend fun updateTag(id : Long, color : String)

    @Query("SELECT * from ITEMS WHERE tags LIKE :tags")
    fun groupByTags(tags: String): Flow<MutableList<Items>>


}