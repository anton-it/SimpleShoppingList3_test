package com.example.simpleshoppinglist3.domain

class GetShopListUseCase(private val shopListRepository: ShopItemRepository) {

    fun getShopList(): List<ShopItem> {

        return shopListRepository.getShopList()

    }
}