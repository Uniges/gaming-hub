package org.gaming.hub.platform.exception

class AuthenticateException private constructor(
    override val message: String
): RuntimeException(message) {
    companion object {
        fun authError(message: String): Nothing =
            throw AuthenticateException(message)
    }
}