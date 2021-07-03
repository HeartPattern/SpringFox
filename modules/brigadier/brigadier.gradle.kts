plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core","1.5.0")

    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper", "paper-mojangapi", "1.16.5-R0.1-SNAPSHOT")

    implementation(project(":common"))
    implementation(project(":core"))

    implementation("org.springframework.boot", "spring-boot-starter", "2.5.2")
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.8")
}

tasks.compileKotlin.get().kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"