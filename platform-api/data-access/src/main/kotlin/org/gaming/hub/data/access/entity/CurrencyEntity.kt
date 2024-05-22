package org.gaming.hub.data.access.entity

import jakarta.persistence.*
import org.gaming.hub.domain.enumeration.CurrencyType

@Entity
@Table(name = "currency", schema = "public")
data class CurrencyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_seq")
    @SequenceGenerator(name = "currency_id_seq", allocationSize = 1)
    val id: Long = 0L,

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    val name: CurrencyType,

    @Column(name = "symbol")
    val symbol: String,

    @Column(name = "denomination")
    val denomination: Int
)
