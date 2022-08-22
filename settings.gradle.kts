pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "PeopleInSpace"

include(":app", ":common")
include(":compose-ios")
include(":compose-kv-shared")
include(":compose-kv-ios")
include(":compose-kv-desktop-jvm")
//include(":wearApp")
//include(":wearApp-benchmark")
//include(":web")
//include(":compose-web")
//include(":backend")
//include(":graphql-server")
