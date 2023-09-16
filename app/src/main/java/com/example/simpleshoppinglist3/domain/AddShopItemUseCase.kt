package com.example.simpleshoppinglist3.domain

class AddShopItemUseCase(private val shopListRepository: ShopItemRepository) {

    fun addShopItem(shopItem: ShopItem) {

        shopListRepository.addShopItem(shopItem)

    }
}