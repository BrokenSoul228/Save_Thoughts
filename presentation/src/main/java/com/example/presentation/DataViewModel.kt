package com.example.presentation

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.entities.Items
import com.example.data.repositories.ItemsRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataViewModel(private val itemRepo : ItemsRepository) : ViewModel() {
    private val listOfAllItems : LiveData<MutableList<Items>> = itemRepo.getAllUsersItem().asLiveData()

    fun returnListItems(tag: String): LiveData<MutableList<Items>> {
        return if (tag.isEmpty()) {
            listOfAllItems
        } else {
            groupByTag(tag)
        }
    }

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

    fun updateTagColors(id: Long, color: String, pos: Int) = viewModelScope.launch {
            itemRepo.updateTagColor(id, color)
    }

    fun deleteItem(id: Long) = viewModelScope.launch {
            itemRepo.deleteSelectedItem(id)
    }

    fun groupByTag(tag : String) : LiveData<MutableList<Items>> = itemRepo.groupByTags(tag).asLiveData()

}