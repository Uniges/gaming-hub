package org.gaming.hub.platform.model.enumeration

import io.swagger.v3.oas.annotations.media.Schema

@Schema
enum class UserGameBalanceOperationType {
    BALANCE,
    DEBIT,
    CREDIT
}