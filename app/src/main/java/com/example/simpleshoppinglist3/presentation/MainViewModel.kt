package com.example.simpleshoppinglist3.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleshoppinglist3.data.ShopListRepositoryImpl
import com.example.simpleshoppinglist3.domain.DeleteShopItemUseCase
import com.example.simpleshoppinglist3.domain.EditShopItemUseCase
import com.example.simpleshoppinglist3.domain.GetShopListUseCase
import com.example.simpleshoppinglist3.domain.MoveShopItemUseCase
import com.example.simpleshoppinglist3.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val moveShopItemUseCase = MoveShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun deleteAllShopItem(shopItemList: MutableList<ShopItem>) {
        for (shopItem in 0 until shopItemList.size) {
            viewModelScope.launch {
                deleteShopItemUseCase.deleteShopItem(shopItemList[shopItem])
            }
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    fun changeAllEnableState(shopItemList: MutableList<ShopItem>) {
        for (shopItem in 0 until shopItemList.size) {
            val newItem = shopItemList[shopItem].copy(enabled = true)
            viewModelScope.launch {
                editShopItemUseCase.editShopItem(newItem)
            }
        }
    }

    fun changeAllDisableState(itemList: MutableList<ShopItem>) {
        for (shopItem in 0 until itemList.size) {
            val newItem = itemList[shopItem].copy(enabled = false)
            viewModelScope.launch {
                editShopItemUseCase.editShopItem(newItem)
            }
        }
    }

    fun moveShopItem(sourcePosition: Int, targetPosition: Int) {
        viewModelScope.launch {
            moveShopItemUseCase.moveShopItem(sourcePosition, targetPosition)
        }
    }
}