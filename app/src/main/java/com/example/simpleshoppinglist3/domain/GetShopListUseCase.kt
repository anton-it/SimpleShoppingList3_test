package com.example.simpleshoppinglist3.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GetShopListUseCase(private val shopListRepository: ShopItemRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {

        return shopListRepository.getShopList()

    }
}