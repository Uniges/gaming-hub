package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query(
        "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u " +
                "WHERE u.id = :userId AND u.login = :login"
    )
    fun existsByIdAndLogin(@Param("userId") userId: Long, @Param("login") login: String): Boolean

    fun findByLogin(login: String): UserEntity?

    @Query("SELECT u.id FROM UserEntity u WHERE u.jwtToken = :jwtToken")
    fun findUserIdByJwtToken(@Param("jwtToken") jwtToken: String): Long?
}