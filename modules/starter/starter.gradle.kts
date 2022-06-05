plugins {
    `publishing-module`
}

dependencies {
    api(project(":common"))
    api(project(":core"))
    api(project(":command"))

    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.KOTLIN_COROUTINE)
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", Versions.KOTLIN_COROUTINE)

    api("org.springframework.boot", "spring-boot-starter", Versions.SPRING)
    api("org.springframework.boot", "spring-boot-starter-aop", Versions.SPRING)
    api("io.github.microutils", "kotlin-logging-jvm", Versions.KOTLIN_LOGGING)
}

configurations.api.get().exclude("org.springframework.boot", "spring-boot-starter-logging")
configurations.api.get().exclude("org.slf4j", "slf4j-api")