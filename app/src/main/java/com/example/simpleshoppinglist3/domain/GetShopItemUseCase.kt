package com.example.simpleshoppinglist3.domain

class GetShopItemUseCase(private val shopListRepository: ShopItemRepository) {

    suspend fun getShopItem(shopItemId: Int): ShopItem {

        return shopListRepository.getShopItem(shopItemId)

    }
}