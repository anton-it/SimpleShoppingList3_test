package com.example.simpleshoppinglist3.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleshoppinglist3.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val count: Int,
    val name: String,
    val enabled: Boolean

)