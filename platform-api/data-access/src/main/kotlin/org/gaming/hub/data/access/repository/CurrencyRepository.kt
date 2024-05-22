package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.CurrencyEntity
import org.gaming.hub.domain.enumeration.CurrencyType
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRepository : JpaRepository<CurrencyEntity, Long> {

    fun existsByName(name: CurrencyType): Boolean
}