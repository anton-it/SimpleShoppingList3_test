package com.example.simpleshoppinglist3.domain

import androidx.lifecycle.LiveData

class MoveShopItemUseCase(private val repository: ShopItemRepository) {

    fun moveShopItem(sourcePosition: Int, targetPosition: Int) {

        repository.moveShopItem(sourcePosition, targetPosition)

    }
}