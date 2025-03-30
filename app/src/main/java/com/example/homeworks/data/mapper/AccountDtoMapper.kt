package com.example.homeworks.data.mapper

import com.example.homeworks.data.remote.AccountDto
import com.example.homeworks.domain.model.Account

fun AccountDto.toDomain(): Account {
    return Account(
        id = this.id,
        accountName = this.accountName,
        accountNumber = this.accountNumber,
        valuteType = this.valuteType,
        cardType = this.cardType,
        balance = this.balance,
        cardLogo = this.cardLogo
    )
}



