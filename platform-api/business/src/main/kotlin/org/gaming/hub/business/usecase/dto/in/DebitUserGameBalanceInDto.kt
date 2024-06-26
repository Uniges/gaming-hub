package org.gaming.hub.business.usecase.dto.`in`

import org.gaming.hub.business.usecase.base.dto.IUserData
import org.gaming.hub.business.usecase.base.dto.IUserRequestData
import org.gaming.hub.domain.enumeration.CurrencyType
import java.util.*

data class DebitUserGameBalanceInDto(
    override val userRequestData: IUserRequestData,
    val gameSessionId: UUID,
    val currencyName: CurrencyType,
    val amount: Long,
    val idempotentToken: String
) : IUserData