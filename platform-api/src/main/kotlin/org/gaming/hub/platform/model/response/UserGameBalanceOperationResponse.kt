package org.gaming.hub.platform.model.response

import org.gaming.hub.domain.enumeration.CurrencyType

data class UserGameBalanceOperationResponse(
    val userName: String,
    val gameName: String,
    val currency: CurrencyType,
    val denomination: Int,
    val balanceAmount: Long,
)
