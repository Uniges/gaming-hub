package org.gaming.hub.platform.model

data class AuthDetails(
    val userId: Long,
    val userName: String,
    val jwtToken: String,
    val idempotentTokens: List<String>
)