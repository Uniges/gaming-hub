package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.UserIdempotentTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserIdempotentTokenRepository : JpaRepository<UserIdempotentTokenEntity, UUID> {
    fun findAllByUserId(userId: Long): List<UserIdempotentTokenEntity>
    fun findByToken(token: String): UserIdempotentTokenEntity?
}