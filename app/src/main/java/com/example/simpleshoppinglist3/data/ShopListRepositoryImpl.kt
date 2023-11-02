package com.example.simpleshoppinglist3.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.simpleshoppinglist3.domain.ShopItem
import com.example.simpleshoppinglist3.domain.ShopItemRepository

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
    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)

    }
    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList())
    {
        mapper.mapListDbModelToListEntity(it)
    }

    override fun moveShopItem(
        sourcePosition: Int,
        targetPosition: Int
    ) {
        // TODO: Add moveUP-Down realisation
//        Collections.swap(shopList, sourcePosition, targetPosition)
//        updateList()
    }

}