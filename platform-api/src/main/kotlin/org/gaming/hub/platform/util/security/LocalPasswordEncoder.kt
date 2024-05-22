package org.gaming.hub.platform.util.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

object LocalPasswordEncoder {
    val encoder: PasswordEncoder = BCryptPasswordEncoder(10)
}