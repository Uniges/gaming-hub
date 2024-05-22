package org.gaming.hub.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("org.gaming.hub")
class PlatformApiApplication

fun main(args: Array<String>) {
	runApplication<PlatformApiApplication>(*args)
}
