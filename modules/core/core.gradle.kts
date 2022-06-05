plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core",Versions.KOTLIN_COROUTINE)
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-jdk8",Versions.KOTLIN_COROUTINE)

    compileOnly("io.papermc.paper", "paper-api", Versions.PAPER)

    implementation(project(":common"))

    implementation("org.springframework.boot", "spring-boot-starter", Versions.SPRING)
    implementation("io.github.microutils", "kotlin-logging-jvm", Versions.KOTLIN_LOGGING)

    compileOnly("com.google.auto.service", "auto-service-annotations", Versions.AUTO_SERVICE)
    kapt("com.google.auto.service", "auto-service", Versions.AUTO_SERVICE)
}

tasks.compileKotlin.get().kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"