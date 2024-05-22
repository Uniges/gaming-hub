package org.gaming.hub.platform.util.mapper

import org.gaming.hub.business.usecase.dto.out.UserGameBalanceOutDto
import org.gaming.hub.platform.model.response.UserGameBalanceOperationResponse

internal fun UserGameBalanceOutDto.toHttpResponse() =
    UserGameBalanceOperationResponse(
        userName = userName,
        gameName = gameName,
        currency = currency,
        denomination = denomination,
        balanceAmount = balanceAmount
    )