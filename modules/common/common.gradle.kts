plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.springframework.boot", "spring-boot-starter", "2.5.2")
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.8")
}