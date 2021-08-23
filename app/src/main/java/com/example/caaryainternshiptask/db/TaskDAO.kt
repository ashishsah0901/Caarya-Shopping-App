package com.example.caaryainternshiptask.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Update
    suspend fun update(item: ShoppingItem)

    @Query("SELECT * FROM shopping_item_table")
    fun getAllItems(): LiveData<List<ShoppingItem>>

}