package com.example.presentation

import android.content.res.Resources
import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entities.Items
import com.example.data.repositories.ItemsRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(private val itemRepo : ItemsRepository) : ViewModel() {
    val currentItem = MutableLiveData<Items>()
    val listOfAllItems = MutableLiveData<MutableList<Items>>()

    fun insertItem(header : String, body : String, tags : String = "white", image: String? = null) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        viewModelScope.launch {
            itemRepo.insertNewItem(
                Items(
                    header = header,
                    mainText = body,
                    date = current,
                    tags = tags,
                    image = image.toString()
                )
            )
            getAllItemsFromDB()
        }
    }

    fun updateCurrentItem(id : Long, header : String, body : String, tags : String = "white", image: String? = null) {
        viewModelScope.launch {
            itemRepo.update(
                Items(
                    id = id,
                    header = header,
                    mainText = body,
                    date = LocalDateTime.now().toString(),
                    tags = tags,
                    image = image
                )
            )
            getAllItemsFromDB()
        }
    }

    fun updateTagColors(id : Long, color : String) {
        viewModelScope.launch {
            itemRepo.updateTagColor(id, color)
            getAllItemsFromDB()
        }
    }

    fun getCurrentItem(id : Long) {
        viewModelScope.launch {
            currentItem.postValue(itemRepo.getCurrentItem(id))
        }
    }

    fun deleteItem(id: Long){
        viewModelScope.launch {
            itemRepo.deleteSelectedItem(id)
            getAllItemsFromDB()
        }
    }

    fun getAllItemsFromDB() {
        viewModelScope.launch {
            listOfAllItems.value = itemRepo.getAllUsersItem()
        }
    }

}