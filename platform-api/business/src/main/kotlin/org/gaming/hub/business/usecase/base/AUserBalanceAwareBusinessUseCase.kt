package org.gaming.hub.business.usecase.base

import org.gaming.hub.business.exception.UseCaseException.Companion.notFoundError
import org.gaming.hub.business.exception.enumeration.UseCaseErrorCode
import org.gaming.hub.business.usecase.base.dto.IUserData
import org.gaming.hub.data.access.entity.UserGameBalanceEntity
import org.gaming.hub.data.access.repository.UserGameBalanceRepository
import org.gaming.hub.data.access.repository.UserRepository
import org.gaming.hub.domain.enumeration.CurrencyType
import org.gaming.hub.domain.projection.UserGameBalanceData
import java.util.*

internal abstract class AUserBalanceAwareBusinessUseCase<IN : IUserData, OUT>(
    private val userRepository: UserRepository,
    private val userGameBalanceRepository: UserGameBalanceRepository,
) : IBusinessUseCaseBase<IN, OUT> {

    final override fun execute(input: IN): OUT {
        checkUserData(
            userId = input.userRequestData.userId,
            login = input.userRequestData.userName
        )
        validateInput(input)
        return executeBusiness(input)
    }

    protected fun getUserGameBalance(
        userId: Long,
        gameSessionId: UUID,
        currencyName: CurrencyType
    ): UserGameBalanceEntity =
        userGameBalanceRepository
            .findUserGameBalance(
                userId = userId,
                gameSessionId = gameSessionId,
                currencyType = currencyName
            ) ?: notFoundError(
            UseCaseErrorCode.REQUEST_DATA_USER_BALANCE_NOT_FOUND,
            userId, gameSessionId, currencyName.name
        )

    protected fun getUserGameBalanceInfo(
        userId: Long,
        gameSessionId: UUID,
        currencyName: CurrencyType
    ): UserGameBalanceData =
        userGameBalanceRepository
            .getGameBalanceInfo(
                userId = userId,
                gameSessionId = gameSessionId,
                currencyType = currencyName
            ) ?: notFoundError(
                UseCaseErrorCode.REQUEST_DATA_USER_BALANCE_NOT_FOUND,
                userId, gameSessionId, currencyName.name
            )


    private fun checkUserData(
        userId: Long,
        login: String,
    ) {
        if (!userRepository.existsByIdAndLogin(
                userId = userId,
                login = login
            )
        ) notFoundError(UseCaseErrorCode.REQUEST_DATA_USER_NOT_FOUND, userId, login)
    }
}