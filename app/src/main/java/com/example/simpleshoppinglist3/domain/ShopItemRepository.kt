package com.example.simpleshoppinglist3.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopItemRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
    fun moveShopItem(sourcePosition: Int, targetPosition: Int)
}