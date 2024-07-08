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

class DataViewModel(private val itemRepo : ItemsRepository) : ViewModel() {
    private val currentItem = MutableLiveData<Items>()
    val listOfAllItems = MutableLiveData<List<Items>>()

    fun insertItem(header : String, body : String, tags : String = "white", image: String? = null) {
        viewModelScope.launch {
            itemRepo.insertNewItem(
                Items(
                    header = header,
                    mainText = body,
                    date = LocalDateTime.now().toString(),
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
        }
    }

    fun getCurrentItem(id : Long) : Items {
        viewModelScope.launch {
            currentItem.value = itemRepo.getCurrentItem(id)
        }
        return currentItem.value!!
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