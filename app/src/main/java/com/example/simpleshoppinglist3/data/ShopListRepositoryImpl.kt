package com.example.simpleshoppinglist3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleshoppinglist3.domain.ShopItem
import com.example.simpleshoppinglist3.domain.ShopItemRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopItemRepository {

    private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id)})
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private var autoIncrementId = 0

    init {
        for(i in 0 until 1000) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("This id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}