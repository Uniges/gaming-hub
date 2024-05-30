package org.gaming.hub.platform.entrypoint

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.gaming.hub.business.usecase.base.IBusinessUseCase
import org.gaming.hub.business.usecase.dto.`in`.CreditUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.`in`.DebitUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.`in`.GetUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.out.UserGameBalanceOutDto
import org.gaming.hub.domain.enumeration.CurrencyType
import org.gaming.hub.platform.model.enumeration.UserGameBalanceOperationType
import org.gaming.hub.platform.model.request.UserGameBalanceOperationRequest
import org.gaming.hub.platform.model.response.UserGameBalanceOperationResponse
import org.gaming.hub.platform.security.UserProvider
import org.gaming.hub.platform.util.mapper.toBalanceUseCaseDto
import org.gaming.hub.platform.util.mapper.toCreditUseCaseDto
import org.gaming.hub.platform.util.mapper.toDebitUseCaseDto
import org.gaming.hub.platform.util.mapper.toHttpResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Tag(name = "USER-BALANCE-ENDPOINT")
class UserGameBalanceProcessorController(
    private val userProvider: UserProvider,
    private val getUserGameBalanceUseCase: IBusinessUseCase<GetUserGameBalanceInDto, UserGameBalanceOutDto>,
    private val debitUserGameBalanceUseCase: IBusinessUseCase<DebitUserGameBalanceInDto, UserGameBalanceOutDto>,
    private val creditUserGameBalanceUseCase: IBusinessUseCase<CreditUserGameBalanceInDto, UserGameBalanceOutDto>,
) {

    @PostMapping("/open-api-games/v1/games-processor")
    @SecurityRequirement(name = "Bearer Authentication")
    fun processRequest(
        /**
         * @RequestBody
         * request: UserGameBalanceOperationRequest,
         * Request params instead of body because of swagger prettier
         */
        @RequestParam(name = "operation")
        operation: UserGameBalanceOperationType,
        @RequestParam(name = "currency")
        currencyType: CurrencyType,
        @RequestParam(name = "game-session-id")
        gameSessionId: UUID,
        @RequestParam(name = "amount")
        amount: Long = 0L,
        @RequestParam(name = "idempotent-token")
        idempotentToken: String = ""
    ): UserGameBalanceOperationResponse {
        val request = UserGameBalanceOperationRequest(operation, currencyType, gameSessionId, amount, idempotentToken)
        val userClaims = userProvider.getJwtClaims()
        return when (request.operation) {
            UserGameBalanceOperationType.BALANCE ->
                getUserGameBalanceUseCase.execute(request.toBalanceUseCaseDto(userClaims))
            UserGameBalanceOperationType.DEBIT ->
                debitUserGameBalanceUseCase.execute(request.toDebitUseCaseDto(userClaims))
            UserGameBalanceOperationType.CREDIT ->
                creditUserGameBalanceUseCase.execute(request.toCreditUseCaseDto(userClaims))
        }.toHttpResponse()
    }
}