package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import org.gaming.hub.domain.enumeration.BalanceTransactionType
import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "balance_transaction", schema = "public")
data class BalanceTransactionEntity(
    @Id
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(generator = "uuid")
    val id: UUID = UUID(0, 0),

    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "game_id")
    var gameId: Long,

    @Column(name = "currency_id")
    var currencyId: Long,

    @Column(name = "amount")
    val amount: Long,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: BalanceTransactionType,

    @Column(name = "idempotent_token")
    val idempotentToken: String,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime,
) {
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns(value = [
        PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id"),
        PrimaryKeyJoinColumn(name = "game_id", referencedColumnName = "game_id"),
        PrimaryKeyJoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    ])
    lateinit var balance: UserGameBalanceEntity
     */
}