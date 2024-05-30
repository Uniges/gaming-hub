package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "user_idempotent_token", schema = "public")
data class UserIdempotentTokenEntity(
    @Id
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(generator = "uuid")
    val id: UUID = UUID(0, 0),

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "token")
    val token: String,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "used")
    var used: Boolean = false
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    lateinit var user: UserEntity
}