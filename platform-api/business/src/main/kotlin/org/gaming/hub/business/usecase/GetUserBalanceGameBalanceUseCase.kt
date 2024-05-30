package org.gaming.hub.business.usecase

import org.gaming.hub.business.exception.UseCaseException.Companion.validationError
import org.gaming.hub.business.exception.enumeration.UseCaseErrorCode
import org.gaming.hub.business.usecase.base.AUserBalanceAwareBusinessUseCase
import org.gaming.hub.business.usecase.dto.`in`.GetUserGameBalanceInDto
import org.gaming.hub.business.usecase.dto.out.UserGameBalanceOutDto
import org.gaming.hub.business.util.mapper.toOutDto
import org.gaming.hub.data.access.repository.CurrencyRepository
import org.gaming.hub.data.access.repository.UserGameBalanceRepository
import org.gaming.hub.data.access.repository.UserIdempotentTokenRepository
import org.gaming.hub.data.access.repository.UserRepository
import org.springframework.stereotype.Service

@Service
internal class GetUserBalanceGameBalanceUseCase(
    userRepository: UserRepository,
    userGameBalanceRepository: UserGameBalanceRepository,
    userIdempotentTokenRepository: UserIdempotentTokenRepository,
    private val currencyRepository: CurrencyRepository,
) : AUserBalanceAwareBusinessUseCase<GetUserGameBalanceInDto, UserGameBalanceOutDto>(
    userRepository = userRepository,
    userGameBalanceRepository = userGameBalanceRepository,
    userIdempotentTokenRepository = userIdempotentTokenRepository
) {

    override fun executeBusiness(input: GetUserGameBalanceInDto): UserGameBalanceOutDto =
        getUserGameBalanceInfo(
            userId = input.userRequestData.userId,
            gameSessionId = input.gameSessionId,
            currencyName = input.currencyName
        ).toOutDto()


    override fun validateInput(input: GetUserGameBalanceInDto) {
        when {
            !currencyRepository.existsByName(input.currencyName) ->
                validationError(UseCaseErrorCode.REQUEST_DATA_CURRENCY_NOT_FOUND, input.currencyName.name)
        }
    }
}