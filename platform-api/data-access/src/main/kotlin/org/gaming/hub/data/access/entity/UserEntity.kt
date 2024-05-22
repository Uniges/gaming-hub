package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "user", schema = "public")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "login")
    val login: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "jwt_token")
    var jwtToken: String? = null,

    @Column(name = "jwt_token_expiry_time")
    var jwtTokenExpiryTime: Long? = null,

    @Column(name = "jp_key")
    var jpKey: UUID? = null,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime,

    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
) {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "user")
    var gameBalances: MutableSet<UserGameBalanceEntity> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "user")
    var gameSessions: MutableSet<GameSessionEntity> = mutableSetOf()
}
