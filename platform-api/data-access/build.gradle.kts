dependencies {
    implementation("org.gaming.hub:domain:1.0")
    // liquibase
    implementation("org.liquibase:liquibase-core")
    // JPA, postgres
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
}