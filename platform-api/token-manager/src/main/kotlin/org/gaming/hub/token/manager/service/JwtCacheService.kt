package org.gaming.hub.token.manager.service

import org.gaming.hub.data.access.repository.UserRepository
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class JwtCacheService(
    private val cacheManager: CacheManager,
    private val userRepository: UserRepository
) {
    companion object {
        const val JWT_TOKEN_USER_ID_CACHE_NAME = "tokenUserCache"
    }

    @Cacheable(cacheNames = [JWT_TOKEN_USER_ID_CACHE_NAME], key = "#hmacToken", sync = true)
    fun getUserIdByHmacToken(hmacToken: String): Long? =
        userRepository.findUserIdByJwtToken(hmacToken)

    @CacheEvict(cacheNames = [JWT_TOKEN_USER_ID_CACHE_NAME], key = "#hmacToken")
    fun evictCacheForHmacToken(hmacToken: String) {}

    fun isHmacTokenInCache(hmacToken: String): Boolean {
        val cache = cacheManager.getCache(JWT_TOKEN_USER_ID_CACHE_NAME)
            ?: return false
        (cache.nativeCache as? Map<String, Long>)?.let {
            return it.containsKey(hmacToken)
        }
        return false
    }
}