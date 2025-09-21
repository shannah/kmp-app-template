import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    // Temporarily commenting out Android to test desktop
    // alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    // Temporarily commenting out Android to test desktop
    // androidTarget {
    //     @OptIn(ExperimentalKotlinGradlePluginApi::class)
    //     compilerOptions {
    //         jvmTarget.set(JvmTarget.JVM_11)
    //     }
    // }

    jvm("desktop") {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        // androidMain.dependencies {
        //     implementation(libs.androidx.compose.ui.tooling.preview)
        //     implementation(libs.androidx.activity.compose)
        //     implementation(libs.ktor.client.okhttp)
        // }
        val desktopMain by getting {
            dependencies {
                // Include all desktop platforms for cross-platform compatibility
                implementation(compose.desktop.linux_x64)
                implementation(compose.desktop.linux_arm64)
                implementation(compose.desktop.macos_x64)
                implementation(compose.desktop.macos_arm64)
                implementation(compose.desktop.windows_x64)
                // Windows ARM64 not yet supported in Compose Multiplatform 1.8.2
                // implementation(compose.desktop.windows_arm64)
                
                implementation(libs.ktor.client.okhttp)
            }
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "KMP App Template"
            packageVersion = "1.0.0"
        }
        
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
    }
}

// Temporarily commenting out Android config
// android {
//     namespace = "com.jetbrains.kmpapp"
//     compileSdk = 35
//
//     defaultConfig {
//         applicationId = "com.jetbrains.kmpapp"
//         minSdk = 24
//         targetSdk = 35
//         versionCode = 1
//         versionName = "1.0"
//     }
//     packaging {
//         resources {
//             excludes += "/META-INF/{AL2.0,LGPL2.1}"
//         }
//     }
//     buildTypes {
//         getByName("release") {
//             isMinifyEnabled = false
//         }
//     }
//     compileOptions {
//         sourceCompatibility = JavaVersion.VERSION_11
//         targetCompatibility = JavaVersion.VERSION_11
//     }
// }

dependencies {
    // debugImplementation(libs.androidx.compose.ui.tooling)
}

tasks.register("buildExecutableJar") {
    dependsOn("createDistributable")
    doLast {
        println("Built executable JAR and distributions")
    }
}
