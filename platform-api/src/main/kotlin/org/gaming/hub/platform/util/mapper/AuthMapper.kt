package org.gaming.hub.platform.util.mapper

import org.gaming.hub.platform.model.business.UserRequestData
import org.gaming.hub.token.manager.model.TokenClaims

fun TokenClaims.toUseCaseDto() = UserRequestData(
    userId = this.userId,
    userName = this.userName
)