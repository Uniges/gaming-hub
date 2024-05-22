import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
}

dependencies {
	implementation(project(":business"))
	implementation(project(":token-manager"))
	// remove!
	implementation(project(":data-access"))
	implementation("org.gaming.hub:domain:1.0")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// security (username and password should be entered before u can access to swagger)
	implementation("org.springframework.boot:spring-boot-starter-security")

	// rest
	implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")


//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//
//
//
//	implementation("commons-codec:commons-codec:1.15")
////	implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
//	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
//	//implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
}

allprojects {
	group = "org.gaming.hub"
	version = "1.0"

	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.springframework.boot")

	java {
		sourceCompatibility = JavaVersion.VERSION_17
	}

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	tasks.getByName<BootJar>("bootJar") {
		enabled = false
	}

	tasks.getByName<Jar>("jar") {
		enabled = true
	}
}