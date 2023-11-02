package com.example.simpleshoppinglist3.domain

class EditShopItemUseCase(private val shopListRepository: ShopItemRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {

        shopListRepository.editShopItem(shopItem)

    }
}