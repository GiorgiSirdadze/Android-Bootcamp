package com.example.homeworks.presentation.mapper

import com.example.homeworks.domain.model.Account
import com.example.homeworks.presentation.model.Card

fun Account.toPresentation(): Card {
    return Card(
        id = this.id,
        accountName = this.accountName,
        accountNumber = this.accountNumber,
        balance = this.balance
    )
}
