package com.example.simpleshoppinglist3.presentation

import androidx.lifecycle.ViewModel
import com.example.simpleshoppinglist3.data.ShopListRepositoryImpl
import com.example.simpleshoppinglist3.domain.AddShopItemUseCase
import com.example.simpleshoppinglist3.domain.EditShopItemUseCase
import com.example.simpleshoppinglist3.domain.GetShopItemUseCase
import com.example.simpleshoppinglist3.domain.ShopItem
import com.example.simpleshoppinglist3.domain.ShopItemRepository
import java.lang.Exception

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    fun getShopItem(shipItemId: Int) {
        getShopItemUseCase.getShopItem(shipItemId)
    }
    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validationInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validationInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validationInput(name: String, count: Int): Boolean {
        var result = true
        if(name.isBlank()) {
            // TODO: Show error input name
            result = false
        }
        if (count <= 0) {
            // TODO: Show error input count
            result = false
        }
        return result
    }
}