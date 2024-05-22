package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.GameEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<GameEntity, Long>