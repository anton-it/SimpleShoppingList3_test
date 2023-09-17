package com.example.simpleshoppinglist3.data

import com.example.simpleshoppinglist3.domain.ShopItem
import com.example.simpleshoppinglist3.domain.ShopItemRepository

object ShopListRepositoryImpl : ShopItemRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    init {
        for(i in 0 until 100) {
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }
    }
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {

        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        shopList.add(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("This id not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList
    }
}