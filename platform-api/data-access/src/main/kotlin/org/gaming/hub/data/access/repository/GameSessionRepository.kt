package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.GameSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameSessionRepository : JpaRepository<GameSessionEntity, UUID>