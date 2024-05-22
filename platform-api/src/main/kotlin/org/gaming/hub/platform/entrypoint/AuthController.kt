package org.gaming.hub.platform.entrypoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.gaming.hub.platform.model.AuthDetails
import org.gaming.hub.platform.security.JwtAuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "USER-AUTH-ENDPOINT")
class AuthController(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {
    @GetMapping("/signIn")
    @Operation(summary = "Authentication")
    fun singIn(
        @RequestParam(name = "username")
        username: String,
        @RequestParam(name = "password")
        password: String
    ): AuthDetails = jwtAuthenticationProvider.authenticate(
        UsernamePasswordAuthenticationToken(username, password)
    ).details as AuthDetails
}