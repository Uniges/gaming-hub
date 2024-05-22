package org.gaming.hub.data.access.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EntityScan("org.gaming.hub.data.access.entity")
@EnableJpaRepositories("org.gaming.hub.data.access.repository")
@EnableTransactionManagement
class DataAccessConfig