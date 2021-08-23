package com.example.caaryainternshiptask.repo

import androidx.lifecycle.LiveData
import com.example.caaryainternshiptask.db.ShoppingItem
import com.example.caaryainternshiptask.db.TaskDAO

class MainRepository(private val taskDao: TaskDAO) {

    val allItems: LiveData<List<ShoppingItem>> = taskDao.getAllItems()

    suspend fun insert(item: ShoppingItem) = taskDao.insert(item)

    suspend fun delete(item: ShoppingItem) = taskDao.delete(item)

    suspend fun update(item: ShoppingItem) = taskDao.update(item)

}