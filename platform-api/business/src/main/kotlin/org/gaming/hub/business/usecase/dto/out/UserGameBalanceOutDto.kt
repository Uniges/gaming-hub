package org.gaming.hub.business.usecase.dto.out

import org.gaming.hub.domain.enumeration.CurrencyType

data class UserGameBalanceOutDto (
    val userName: String,
    val gameName: String,
    val currency: CurrencyType,
    val denomination: Int,
    val balanceAmount: Long,
)