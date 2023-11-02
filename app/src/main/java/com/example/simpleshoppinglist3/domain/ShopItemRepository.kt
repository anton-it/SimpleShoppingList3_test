package com.example.simpleshoppinglist3.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopItemRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
    fun moveShopItem(sourcePosition: Int, targetPosition: Int)
}