package com.example.caaryainternshiptask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [ShoppingItem::class], version = 1, exportSchema = false)
abstract class ShoppingItemDatabase: RoomDatabase() {
    abstract fun getClassDao(): TaskDAO
    companion object{
        @Volatile
        private var INSTANCE: ShoppingItemDatabase? = null
        fun getDatabase(context: Context): ShoppingItemDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingItemDatabase::class.java,
                    "shopping_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}