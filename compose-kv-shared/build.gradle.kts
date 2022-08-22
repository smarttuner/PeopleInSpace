import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin(multiplatform)
    id(androidLib)
    id(composePlugin) version Versions.COMPOSE_MULTIPLATFORM_PLUGIN
}

version = "1.0"

kotlin {
    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64 // available to KT 1.5.30
        else -> ::iosX64
    }
    iosTarget("iOS") {}

    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Deps.KaffeeVerde.ApplicationSupport)
                api(Deps.KaffeeVerde.ComposeHelper)
                api(Deps.KaffeeVerde.LifecycleRuntimeCompose)
                implementation(project(":common"))
                with(compose){
                    implementation(runtime)
                    implementation(foundation)
                    implementation(ui)
                    implementation(material)
                    implementation(animation)
                    implementation(materialIconsExtended)
                }
                with(Deps.Koin) {
                    implementation(core)
                }
            }


        }
        val commonTest by getting
        val desktopMain by getting

        val iOSMain by getting {
            dependsOn(commonMain)
        }

        val androidTest by getting

    }
}

android {
    compileSdk =  Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}


kotlin {
    targets.withType<KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}
