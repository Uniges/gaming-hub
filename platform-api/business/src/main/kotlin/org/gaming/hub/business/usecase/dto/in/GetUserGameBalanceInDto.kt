package org.gaming.hub.business.usecase.dto.`in`

import org.gaming.hub.business.usecase.base.dto.IUserData
import org.gaming.hub.business.usecase.base.dto.IUserRequestData
import org.gaming.hub.domain.enumeration.CurrencyType
import java.util.*

data class GetUserGameBalanceInDto(
    override val userRequestData: IUserRequestData,
    val gameSessionId: UUID,
    val currencyName: CurrencyType
) : IUserData