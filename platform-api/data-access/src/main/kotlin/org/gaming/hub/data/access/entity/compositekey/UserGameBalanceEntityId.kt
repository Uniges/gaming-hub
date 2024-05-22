package org.gaming.hub.data.access.entity.compositekey

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class UserGameBalanceEntityId (
    @Column(name = "user_id")
    var userId: Long,
    @Column(name = "game_id")
    var gameId: Long,
    @Column(name = "currency_id")
    var currencyId: Long,
) : Serializable