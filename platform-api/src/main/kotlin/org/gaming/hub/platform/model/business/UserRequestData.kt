package org.gaming.hub.platform.model.business

import org.gaming.hub.business.usecase.base.dto.IUserRequestData

class UserRequestData(
    override val userId: Long,
    override val userName: String
) : IUserRequestData