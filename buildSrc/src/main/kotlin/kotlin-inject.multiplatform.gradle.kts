import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithTests
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    kotlin("multiplatform")
}

val nativeTargets = arrayOf(
    "linuxX64",
    "macosX64", "macosArm64",
    "iosArm32", "iosArm64", "iosX64", "iosSimulatorArm64",
    "tvosArm64", "tvosX64", "tvosSimulatorArm64",
    "watchosArm32", "watchosArm64", "watchosX86", "watchosX64", "watchosSimulatorArm64",
)

kotlin {
    jvm()
    js(BOTH) {
        browser()
        nodejs()
        // suppress noisy 'Reflection is not supported in JavaScript target'
        for (compilation in arrayOf("main", "test")) {
            compilations.getByName(compilation).kotlinOptions {
                suppressWarnings = true
            }
        }
    }

    for (target in nativeTargets) {
       targets.add(presets.getByName(target).createTarget(target))
    }

    sourceSets {
        val nativeMain by creating {
            dependsOn(commonMain.get())
        }
        val nativeTest by creating {
            dependsOn(commonTest.get())
        }
        for (sourceSet in nativeTargets) {
            getByName("${sourceSet}Main") {
                dependsOn(nativeMain)
            }
            getByName("${sourceSet}Test") {
                dependsOn(nativeTest)
            }
        }
    }
}

// Run only the native tests
val nativeTest by tasks.registering {
    kotlin.targets.all {
        if (this is KotlinNativeTargetWithTests<*>) {
            dependsOn("${name}Test")
        }
    }
}

// Disable as ksp doesn't support js ir
tasks.withType<KotlinJsCompile>().configureEach {
    if (name.contains("Test") && name.contains("Ir")) {
        disableAndWarn()
    }
}
tasks.withType<KotlinJsTest>().configureEach {
    if (name.contains("Ir")) {
        disableAndWarn()
    }
}

fun Task.disableAndWarn() {
    enabled = false
    logger.warn("disabling: $name as ksp does not support js ir https://github.com/JetBrains/kotlin/pull/4264")
}