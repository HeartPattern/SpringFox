plugins {
    `publishing-module`
}

dependencies {
    api(project(":common"))
    api(project(":core"))
    api(project(":brigadier"))

    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.5.0")
    api("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", "1.5.0")

    api("org.springframework.boot", "spring-boot-starter", "2.5.2")
    api("org.springframework.boot", "spring-boot-starter-aop", "2.5.2")
    api("io.github.microutils", "kotlin-logging-jvm", "2.0.8")
}

configurations.api.get().exclude("org.springframework.boot", "spring-boot-starter-logging")
configurations.api.get().exclude("org.slf4j", "slf4j-api")