package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Items (

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,

    @ColumnInfo(name = "header") var header : String,

    @ColumnInfo(name = "main_text") var mainText : String,

    @ColumnInfo(name = "date") var date : String,

    @ColumnInfo(name = "tags") var tags : String,

    @ColumnInfo(name = "image") var image : String?
)