package com.example.simpleshoppinglist3.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.simpleshoppinglist3.domain.ShopItem
import com.example.simpleshoppinglist3.domain.ShopItemRepository
import com.example.simpleshoppinglist3.presentation.ShopListAdapter
import java.util.Collections
import java.util.TreeSet
import kotlin.random.Random
class ShopListRepositoryImpl(
    application: Application
) : ShopItemRepository {
    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

//    private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id)})
//////    private var shopList = mutableListOf<ShopItem>()
//    private val shopListLD = MutableLiveData<List<ShopItem>>()
//
//    private var autoIncrementId = 0
//
//    init {
//        for(i in 0 until 10) {
//            val item = ShopItem("Name $i", i, Random.nextBoolean())
//            addShopItem(item)
//        }
//    }
    override fun addShopItem(shopItem: ShopItem) {
//        if (shopItem.id == ShopItem.UNDEFINED_ID) {
//            shopItem.id = autoIncrementId++
//        }
//        shopList.add(shopItem)
//        updateList()
        shopListDao.addShopIem(mapper.mupEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
//        shopList.remove(shopItem)
//        updateList()
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun moveShopItem(
        sourcePosition: Int,
        targetPosition: Int
    ) {
        // TODO: Add moveUP-Down realisation
//        Collections.swap(shopList, sourcePosition, targetPosition)
//        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
//        val oldShopItem = getShopItem(shopItem.id)
//        shopList.remove(oldShopItem)
//        addShopItem(shopItem)
        shopListDao.addShopIem(mapper.mupEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
//        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("This id not found")
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList())
    {
        mapper.mapListDbModelToListEntity(it)
//        return shopListLD
    }

//    private fun updateList() {
//        shopListLD.value = shopList.toList()
//    }
}