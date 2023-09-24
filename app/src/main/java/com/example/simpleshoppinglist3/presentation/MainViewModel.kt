package com.example.simpleshoppinglist3.presentation

import androidx.lifecycle.ViewModel
import com.example.simpleshoppinglist3.data.ShopListRepositoryImpl
import com.example.simpleshoppinglist3.domain.DeleteShopItemUseCase
import com.example.simpleshoppinglist3.domain.EditShopItemUseCase
import com.example.simpleshoppinglist3.domain.GetShopListUseCase
import com.example.simpleshoppinglist3.domain.MoveShopItemUseCase
import com.example.simpleshoppinglist3.domain.ShopItem
import java.util.Collections

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val moveShopItemUseCase = MoveShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem ) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

    fun moveShopItem(sourcePosition: Int, targetPosition: Int) {
        moveShopItemUseCase.moveShopItem(sourcePosition, targetPosition)
    }

}