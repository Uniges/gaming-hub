package org.gaming.hub.business.util.mapper

import org.gaming.hub.business.usecase.dto.out.UserGameBalanceOutDto
import org.gaming.hub.domain.projection.UserGameBalanceData

fun UserGameBalanceData.toOutDto() =
    UserGameBalanceOutDto(
        userName = userName,
        gameName = gameName,
        currency = currency,
        denomination = denomination,
        balanceAmount = balanceAmount
    )