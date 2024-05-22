package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import org.gaming.hub.data.access.entity.compositekey.UserGameBalanceEntityId
import java.time.OffsetDateTime

@Entity
@Table(name = "user_game_balance", schema = "public")
data class UserGameBalanceEntity(
    @EmbeddedId
    val id: UserGameBalanceEntityId,

    @Column(name = "amount")
    var amount: Long,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime,

    @Version
    @Column(name = "version")
    var version: Long = 0L,

    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    lateinit var user: UserEntity
}
