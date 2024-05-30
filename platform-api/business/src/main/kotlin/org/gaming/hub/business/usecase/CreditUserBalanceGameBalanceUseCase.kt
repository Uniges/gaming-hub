package org.gaming.hub.business.usecase

import org.gaming.hub.business.exception.UseCaseException.Companion.internalError
import org.gaming.hub.business.exception.UseCaseException.Companion.validationError
import org.gaming.hub.business.exception.enumeration.UseCaseErrorCode
import org.gaming.hub.business.usecase.base.AUserBalanceAwareBusinessUseCase
import org.gaming.hub.business.usecase.dto.`in`.CreditUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.out.UserGameBalanceOutDto
import org.gaming.hub.business.util.mapper.toOutDto
import org.gaming.hub.data.access.entity.BalanceTransactionEntity
import org.gaming.hub.data.access.repository.*
import org.gaming.hub.domain.enumeration.BalanceTransactionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
internal class CreditUserBalanceGameBalanceUseCase(
    userRepository: UserRepository,
    private val currencyRepository: CurrencyRepository,
    private val userGameBalanceRepository: UserGameBalanceRepository,
    private val balanceTransactionRepository: BalanceTransactionRepository,
    private val userIdempotentTokenRepository: UserIdempotentTokenRepository,
) : AUserBalanceAwareBusinessUseCase<CreditUserGameBalanceInDto, UserGameBalanceOutDto>(
    userRepository = userRepository,
    userGameBalanceRepository = userGameBalanceRepository,
    userIdempotentTokenRepository = userIdempotentTokenRepository
) {

    @Transactional
    override fun executeBusiness(input: CreditUserGameBalanceInDto): UserGameBalanceOutDto {
        val idempotentToken = validateAndGetIdempotentToken(input.userRequestData.userId, input.idempotentToken)

        val balance = getUserGameBalance(input.userRequestData.userId, input.gameSessionId, input.currencyName)

        if (balance.amount < input.amount) {
            internalError(
                UseCaseErrorCode.USER_DOES_NOT_HAVE_ENOUGH_MONEY,
                input.userRequestData.userId, balance.amount, input.amount
            )
        }

        balance.amount -= input.amount
        userGameBalanceRepository.save(balance)

        val transaction = BalanceTransactionEntity(
            userId = balance.id.userId,
            gameId = balance.id.gameId,
            currencyId = balance.id.currencyId,
            amount = input.amount,
            type = BalanceTransactionType.CREDIT,
            createdAt = OffsetDateTime.now(),
            idempotentToken = input.idempotentToken
        )
        balanceTransactionRepository.save(transaction)

        idempotentToken.used = true
        userIdempotentTokenRepository.save(idempotentToken)

        return getUserGameBalanceInfo(
            userId = input.userRequestData.userId,
            gameSessionId = input.gameSessionId,
            currencyName = input.currencyName
        ).toOutDto()

    }

    override fun validateInput(input: CreditUserGameBalanceInDto) {
        when {
            !currencyRepository.existsByName(input.currencyName) ->
                validationError(UseCaseErrorCode.REQUEST_DATA_CURRENCY_NOT_FOUND, input.currencyName.name)

            input.amount <= 0 ->
                validationError(UseCaseErrorCode.REQUEST_DATA_OPERATION_AMOUNT_LESS_THEN_OR_ZERO, input.amount)
        }
    }
}