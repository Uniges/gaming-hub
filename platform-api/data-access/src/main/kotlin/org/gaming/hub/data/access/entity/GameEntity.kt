package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "game", schema = "public")
data class GameEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_seq")
    @SequenceGenerator(name = "game_id_seq", allocationSize = 1)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime,

    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
)
