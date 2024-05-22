package org.gaming.hub.token.manager.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class HmacService(
//    @Value("\${HMAC_SECRET}")
    private val secretKey: String = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf"
) {
    private lateinit var mac: Mac

    init {
        try {
            mac = Mac.getInstance("HmacSHA256")
            val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
            mac.init(secretKeySpec)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to initialize HmacService", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to initialize HmacService", e)
        }
    }

    fun generateHmacSignature(token: String): String {
        val hmacData = mac.doFinal(token.toByteArray())
        return Base64.getEncoder().encodeToString(hmacData)
    }
}