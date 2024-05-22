plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "platform-api"

/**
 * Platform modules (Platform -> Business -> Data-access)
 */
include("business")
include("data-access")
include("token-manager")

/**
 * Shared libraries
 */
includeBuild("shared/domain")
includeBuild("shared/aspect-logger")

