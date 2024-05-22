package org.gaming.hub.token.manager.exception

class TokenAccessException private constructor(
    override val message: String
): RuntimeException(message) {
    companion object {
        fun tokenError(message: String): Nothing =
            throw TokenAccessException(message)
    }
}