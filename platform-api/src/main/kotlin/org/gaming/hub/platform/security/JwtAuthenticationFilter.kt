package org.gaming.hub.platform.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.gaming.hub.token.manager.service.JwtTokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenService: JwtTokenService
) : OncePerRequestFilter() {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_AUTH_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION_HEADER)

        if (header == null || !header.startsWith(BEARER_AUTH_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.substring(BEARER_AUTH_PREFIX.length)
        val authentication = jwtTokenService.validateTokenAndGetClaims(token).let {
            UsernamePasswordAuthenticationToken(
                "",
                null,
                emptyList()
            ).apply {
                details = it
            }
        }

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}