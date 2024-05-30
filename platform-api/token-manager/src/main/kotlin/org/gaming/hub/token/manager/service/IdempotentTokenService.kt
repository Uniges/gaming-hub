package org.gaming.hub.token.manager.service

import org.gaming.hub.data.access.entity.UserIdempotentTokenEntity
import org.gaming.hub.data.access.repository.UserIdempotentTokenRepository
import org.springframework.stereotype.Service

@Service
class IdempotentTokenService(
    private val userIdempotentTokenRepository: UserIdempotentTokenRepository,
) {
    private val tokenLength: Int = 32
    private val tokenCharset: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generateTokens(userId: Long, numberOfTokens: Int): List<String> {
        val tokens = generateTokens(numberOfTokens)
        tokens.map { token ->
            UserIdempotentTokenEntity(
                userId = userId, token = token
            )
        }.also { userIdempotentTokenRepository.saveAll(it) }
        return tokens
    }

    fun deleteOldTokens(userId: Long) {
        val oldTokens = userIdempotentTokenRepository.findAllByUserId(userId)
        userIdempotentTokenRepository.deleteAll(oldTokens)
    }

    private fun generateTokens(count: Int): List<String> {
        return (1..count)
            .map { generateToken() }
    }

    private fun generateToken(): String {
        return (1..tokenLength)
            .map { tokenCharset.random() }
            .joinToString("")
    }
}