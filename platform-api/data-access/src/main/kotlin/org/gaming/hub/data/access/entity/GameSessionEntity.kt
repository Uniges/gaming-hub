package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import org.gaming.hub.domain.enumeration.GameSessionStatus
import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "game_session", schema = "public")
data class GameSessionEntity(
    @Id
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(generator = "uuid")
    val id: UUID = UUID(0, 0),

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "game_id")
    val gameId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: GameSessionStatus,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime,

    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    lateinit var user: UserEntity
}
