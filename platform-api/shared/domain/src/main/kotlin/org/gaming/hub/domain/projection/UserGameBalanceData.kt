package org.gaming.hub.domain.projection

import org.gaming.hub.domain.enumeration.CurrencyType

data class UserGameBalanceData(
    val userName: String,
    val gameName: String,
    val currency: CurrencyType,
    val denomination: Int,
    val balanceAmount: Long,
)
