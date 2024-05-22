package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.UserGameBalanceEntity
import org.gaming.hub.data.access.entity.compositekey.UserGameBalanceEntityId
import org.gaming.hub.domain.enumeration.CurrencyType
import org.gaming.hub.domain.projection.UserGameBalanceData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserGameBalanceRepository : JpaRepository<UserGameBalanceEntity, UserGameBalanceEntityId> {
    @Query("SELECT ugb FROM UserGameBalanceEntity ugb WHERE ugb.id.userId = :userId AND " +
            "ugb.id.gameId = (SELECT gs.gameId FROM GameSessionEntity gs WHERE gs.id = :gameSessionId) AND " +
            "ugb.id.currencyId = (SELECT c.id FROM CurrencyEntity c WHERE c.name = :currencyType)")
    fun findUserGameBalance(
        @Param("userId") userId: Long,
        @Param("gameSessionId") gameSessionId: UUID,
        @Param("currencyType") currencyType: CurrencyType,
    ): UserGameBalanceEntity?

    @Query("""
        SELECT new org.gaming.hub.domain.projection.UserGameBalanceData(
            u.login,
            g.name,
            :currencyType,
            c.denomination,
            ugb.amount
        )
        FROM UserGameBalanceEntity ugb
        JOIN UserEntity u ON u.id = ugb.id.userId
        JOIN GameEntity g ON g.id = (SELECT gs.gameId FROM GameSessionEntity gs WHERE gs.id = :gameSessionId)
        JOIN CurrencyEntity c ON c.id = (SELECT c.id FROM CurrencyEntity c WHERE c.name = :currencyType)
        WHERE ugb.id.userId = :userId
          AND ugb.id.currencyId = c.id
          AND ugb.id.gameId = g.id
    """)
    fun getGameBalanceInfo(
        @Param("userId") userId: Long,
        @Param("gameSessionId") gameSessionId: UUID,
        @Param("currencyType") currencyType: CurrencyType,
    ): UserGameBalanceData?

    @Modifying
    @Query("UPDATE UserGameBalanceEntity ugb " +
            "SET ugb.amount = ugb.amount + :amount, ugb.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE ugb.id = :id")
    fun increaseBalance(
        @Param("id") id: UserGameBalanceEntityId,
        @Param("amount") amount: Long
    ): Int
}