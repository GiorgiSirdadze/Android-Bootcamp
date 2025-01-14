package com.example.homeworks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworks.carditem.Card

class PaymentViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    fun addCard(card: Card) {
        val currentList = _cards.value?.toMutableList() ?: mutableListOf()
        currentList.add(card)
        _cards.value = currentList
    }

    fun deleteCard(cardId: Int) {
        val currentList = _cards.value?.toMutableList() ?: mutableListOf()
        val cardToDelete = currentList.find { it.cardId == cardId }

        cardToDelete?.let {
            currentList.remove(it)
            _cards.value = currentList
        }
    }
}
