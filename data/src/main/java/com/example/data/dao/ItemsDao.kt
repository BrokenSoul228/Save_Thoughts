package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entities.Items

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : Items)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item : Items)

    @Query("DELETE FROM items WHERE id =:id")
    fun delete(id : Long)

    @Query("SELECT * from items WHERE id = :id")
    fun getCurrentItem(id : Long) : Items

    @Query("SELECT * from items")
    fun getAllNotes() : MutableList<Items>

    @Query("UPDATE ITEMS SET tags = :color WHERE id = :id")
    fun updateTag(id : Long, color : String)

}