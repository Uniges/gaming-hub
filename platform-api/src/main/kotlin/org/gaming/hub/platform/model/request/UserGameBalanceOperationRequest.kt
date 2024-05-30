package org.gaming.hub.platform.model.request

import org.gaming.hub.domain.enumeration.CurrencyType
import org.gaming.hub.platform.model.enumeration.UserGameBalanceOperationType
import java.util.*

data class UserGameBalanceOperationRequest (
    val operation: UserGameBalanceOperationType,
    val currencyType: CurrencyType,
    val gameSessionId: UUID,
    val amount: Long = 0L,
    val idempotentToken: String = ""
)