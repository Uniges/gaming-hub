package org.gaming.hub.platform.util.mapper

import org.gaming.hub.business.usecase.base.dto.IUserRequestData
import org.gaming.hub.business.usecase.dto.`in`.CreditUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.`in`.DebitUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.`in`.GetUserGameBalanceInDto
import org.gaming.hub.platform.model.business.UserRequestData
import org.gaming.hub.platform.model.request.UserGameBalanceOperationRequest
import org.gaming.hub.token.manager.model.TokenClaims


internal fun UserGameBalanceOperationRequest.toBalanceUseCaseDto(claims: TokenClaims) =
    GetUserGameBalanceInDto(
        userRequestData = claims.toUseCaseDto(),
        gameSessionId = gameSessionId,
        currencyName = currencyType
    )

internal fun UserGameBalanceOperationRequest.toDebitUseCaseDto(claims: TokenClaims) =
    DebitUserGameBalanceInDto(
        userRequestData = claims.toUseCaseDto(),
        gameSessionId = gameSessionId,
        currencyName = currencyType,
        amount = amount
    )

internal fun UserGameBalanceOperationRequest.toCreditUseCaseDto(claims: TokenClaims) =
    CreditUserGameBalanceInDto(
        userRequestData = claims.toUseCaseDto(),
        gameSessionId = gameSessionId,
        currencyName = currencyType,
        amount = amount
    )