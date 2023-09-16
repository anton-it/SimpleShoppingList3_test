package com.example.simpleshoppinglist3.domain

class EditShopItemUseCase(private val shopListRepository: ShopItemRepository) {

    fun editShopItem(shopItem: ShopItem) {

        shopListRepository.editShopItem(shopItem)

    }
}