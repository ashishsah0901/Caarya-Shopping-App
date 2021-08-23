package com.example.caaryainternshiptask.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caaryainternshiptask.db.ShoppingItem
import com.example.caaryainternshiptask.db.ShoppingItemDatabase
import com.example.caaryainternshiptask.repo.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {

    private val repository: MainRepository
    val allItems: LiveData<List<ShoppingItem>>

    init {
        val dao = ShoppingItemDatabase.getDatabase(application).getClassDao()
        repository = MainRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: ShoppingItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(item: ShoppingItem) = viewModelScope.launch {
        repository.delete(item)
    }

    fun update(item: ShoppingItem) = viewModelScope.launch {
        repository.update(item)
    }

}