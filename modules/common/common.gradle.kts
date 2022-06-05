plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.springframework.boot", "spring-boot-starter", Versions.SPRING)
    implementation("io.github.microutils", "kotlin-logging-jvm", Versions.KOTLIN_LOGGING)
}