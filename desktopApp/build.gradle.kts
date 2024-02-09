import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("dev.hydraulic.conveyor") version "1.8"
}

/* For Conveyor */
version = rootProject.version

kotlin {
    jvm {

        java {
            sourceCompatibility = JavaVersion.VERSION_18
            targetCompatibility = JavaVersion.VERSION_18
        }

        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_18.toString()
        }
    }
    sourceSets {
        val jvmMain by getting  {
            dependencies {

                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
        }
    }
}

dependencies {
    // Use the configurations created by the Conveyor plugin to tell Gradle/Conveyor where to find the artifacts for each platform.
    macAmd64(compose.desktop.macos_x64)

    // TODO
    // macAarch64(compose.desktop.macos_arm64)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion
