pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "kotlin-inject"

include(":kotlin-inject-runtime")
include(":kotlin-inject-compiler:core")
project(":kotlin-inject-compiler:core").name = "kotlin-inject-compiler-core"
include(":kotlin-inject-compiler:kapt")
project(":kotlin-inject-compiler:kapt").name = "kotlin-inject-compiler-kapt"
include(":kotlin-inject-compiler:ksp")
project(":kotlin-inject-compiler:ksp").name = "kotlin-inject-compiler-ksp"
include(":kotlin-inject-compiler:test")
include(":ast:core")
project(":ast:core").name = "ast-core"
include(":ast:ksp")
project(":ast:ksp").name = "ast-ksp"
include(":ast:kapt")
project(":ast:kapt").name = "ast-kapt"
include(":integration-tests:kapt")
include(":integration-tests:ksp")
include(":integration-tests:module")
include(":integration-tests:kapt-companion")
include(":integration-tests:ksp-companion")
include(":integration-tests:jmh")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.6.0")
}