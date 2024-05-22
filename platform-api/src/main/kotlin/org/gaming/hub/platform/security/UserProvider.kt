package org.gaming.hub.platform.security

import org.gaming.hub.platform.exception.AuthenticateException.Companion.authError
import org.gaming.hub.platform.util.security.LocalPasswordEncoder
import org.gaming.hub.token.manager.model.TokenClaims
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserProvider {
    fun getJwtClaims(): TokenClaims =
        SecurityContextHolder.getContext().authentication.details as? TokenClaims
            ?: authError("Security contains wrong claims")
}