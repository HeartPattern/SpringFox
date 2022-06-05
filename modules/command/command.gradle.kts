plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.KOTLIN_COROUTINE)

    compileOnly("io.papermc.paper", "paper-api", Versions.PAPER)
    compileOnly("io.papermc.paper", "paper-mojangapi", Versions.PAPER)

    implementation(project(":common"))
    implementation(project(":core"))

    implementation("org.springframework.boot", "spring-boot-starter", Versions.SPRING)
    implementation("io.github.microutils", "kotlin-logging-jvm", Versions.KOTLIN_LOGGING)
}

tasks.compileKotlin.get().kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"