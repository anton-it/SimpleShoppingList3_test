package com.example.simpleshoppinglist3.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopItemRepository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}