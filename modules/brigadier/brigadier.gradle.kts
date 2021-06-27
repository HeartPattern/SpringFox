plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
    implementation("com.destroystokyo.paper", "paper-mojangapi", "1.16.5-R0.1-SNAPSHOT")

    implementation(project(":common"))
    implementation(project(":core"))

    implementation("org.springframework.boot", "spring-boot-starter", "2.4.5")
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.8")
}

tasks.compileKotlin.get().kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"