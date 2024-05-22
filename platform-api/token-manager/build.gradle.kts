dependencies {
    implementation(project(":data-access"))
    implementation("org.springframework:spring-context")
    implementation("org.gaming.hub:aspect-logger:1.0")
    // JWT token, strongly depends on version!
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}