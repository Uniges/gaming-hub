package org.gaming.hub.platform.security

import org.gaming.hub.data.access.repository.UserRepository
import org.gaming.hub.platform.exception.AuthenticateException.Companion.authError
import org.gaming.hub.platform.model.AuthDetails
import org.gaming.hub.platform.util.security.LocalPasswordEncoder
import org.gaming.hub.token.manager.model.TokenClaims
import org.gaming.hub.token.manager.service.IdempotentTokenService
import org.gaming.hub.token.manager.service.JwtTokenService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    private val userRepository: UserRepository,
    private val jwtTokenService: JwtTokenService,
    private val idempotentTokenService: IdempotentTokenService,
) : AuthenticationProvider {

    private companion object {
        const val USER_IDEMPOTENT_TOKEN_COUNT = 10
    }

    override fun authenticate(authentication: Authentication): Authentication {
        if (!authentication.isAuthenticated) {
            val user = userRepository.findByLogin(authentication.name)
                ?: authError("Wrong credentials")

            if (!LocalPasswordEncoder.encoder.matches(authentication.credentials.toString(), user.password)) {
                authError("Wrong credentials")
            }

            val generatedToken = jwtTokenService.generateToken(
                TokenClaims(
                    userId = user.id,
                    userName = user.login
                )
            )

            // remove old token
            user.jwtToken?.let { oldToken ->
                jwtTokenService.tryRemoveHmacTokenFromCache(oldToken)
            }

            // remove old user's idempotent tokens
            idempotentTokenService.deleteOldTokens(user.id)

            val createdIdempotentTokens = idempotentTokenService.generateTokens(user.id, USER_IDEMPOTENT_TOKEN_COUNT)

            // update user data
            user.jwtToken = jwtTokenService.getHmacFromRawToken(generatedToken.token)
            user.jwtTokenExpiryTime = generatedToken.expiryDate.time
            userRepository.saveAndFlush(user)

            return UsernamePasswordAuthenticationToken(
                authentication.name,
                authentication.credentials,
                authentication.authorities
            ).apply {
                details = AuthDetails(
                    userId = user.id,
                    userName = user.login,
                    jwtToken = generatedToken.token,
                    idempotentTokens = createdIdempotentTokens
                )
            }
        }
        return authentication
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class.java
    }

}