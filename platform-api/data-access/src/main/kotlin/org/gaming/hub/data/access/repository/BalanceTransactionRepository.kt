package org.gaming.hub.data.access.repository

import org.gaming.hub.data.access.entity.BalanceTransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BalanceTransactionRepository : JpaRepository<BalanceTransactionEntity, UUID>