package org.gaming.hub.token.manager.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.gaming.hub.token.manager.exception.TokenAccessException.Companion.tokenError
import org.gaming.hub.token.manager.model.GeneratedToken
import org.gaming.hub.token.manager.model.TokenClaims
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenService(
//    @Value("\${JWT_SECRET}")
//    private val secret: String,
//    @Value("\${JWT_LIFETIME}")
//    private val jwtLifeTime: Duration,

    private val jwtCacheService: JwtCacheService,
    private val hmacService: HmacService,
) {
    private val secret: String = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf"
    private val jwtLifeTime: Duration = Duration.ofDays(1L)

    private companion object {
        const val USER_ID_KEY = "id"
        const val USER_NAME_KEY = "username"
    }

    private val secretKey = SecretKeySpec(
        Base64.getDecoder().decode(secret),
        SignatureAlgorithm.HS256.jcaName
    )

    fun generateToken(tokenClaims: TokenClaims): GeneratedToken {
        //val claims = mapOf("roles" to "USER")
        val issueDate = Date()
        val expiredDate = Date(issueDate.time + jwtLifeTime.toMillis())

        val token = Jwts.builder()
            .setClaims(
                mapOf(
                    USER_ID_KEY to tokenClaims.userId,
                    USER_NAME_KEY to tokenClaims.userName
                )
            )
            .setIssuedAt(issueDate)
            .setExpiration(expiredDate)
            .signWith(secretKey)
            .compact()

        return GeneratedToken(
            token = token,
            expiryDate = expiredDate
        )
    }

    /**
     * Validates token, puts it in cache (if not in) and returns claims
     */
    fun validateTokenAndGetClaims(token: String): TokenClaims {
        getTokenFromCache(token) ?: run {
            tokenError("token doesn't exist token=[$token]")
        }

        val claims = try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        } catch (e: Exception) {
            tokenError(e.localizedMessage)
        }

        val tokenClaims = TokenClaims(
            userId = claims[USER_ID_KEY]?.toString()?.toLong()
                ?: tokenError("token doesn't contain userId claim"),
            userName = claims[USER_NAME_KEY]?.toString()
                ?: tokenError("token doesn't contain userName claim")
        )

        return tokenClaims
    }

    fun tryRemoveHmacTokenFromCache(hmacToken: String) =
        jwtCacheService.evictCacheForHmacToken(
            hmacToken = hmacToken
        )

    /**
     * Returns userId from in-memory cache.
     *
     * If the cache doesn't contain the current token,
     * it will try to get it from a database and caches immediately.
     *
     * Not existed or invalid token will cache too
     * and a database will not be asked again.
     *
     * @param token raw token
     * @return founded userId or null
     */
    fun getUserIdFromCacheByToken(token: String): Long? =
        getTokenFromCache(token)

    fun getUserIdFromCacheByTokenOld(token: String): Long? =
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
                ?.get(USER_ID_KEY).toString().toLong()
        } catch (e: Exception) {
            null
        }

    fun getHmacFromRawToken(token: String): String =
        hmacService.generateHmacSignature(token)

    @Synchronized
    private fun getTokenFromCache(token: String): Long? =
        jwtCacheService.getUserIdByHmacToken(
            hmacToken = hmacService.generateHmacSignature(token)
        )
}