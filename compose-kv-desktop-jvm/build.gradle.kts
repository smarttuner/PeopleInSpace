import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
plugins {
    kotlin(multiplatform)
    id(composePlugin) version Versions.COMPOSE_MULTIPLATFORM_PLUGIN
}

group = "me.joreilly"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting

        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                implementation(compose.desktop.currentOs)
                api(project(":compose-kv-shared"))
            }
        }
    }
}

compose.desktop {
    application() {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "People In Space"
            macOS {
                bundleID = "me.joreilly"
            }
        }
    }
}

