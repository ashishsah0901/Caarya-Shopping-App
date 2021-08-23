package com.example.caaryainternshiptask.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_item_table")
data class ShoppingItem(
        var name: String = "",
        var marketPrice: Long = 0L,
        var storePrice: Long = 0L,
        var category: String? = "Other",
        var description: String? = "",
        var image: Bitmap? = null
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
