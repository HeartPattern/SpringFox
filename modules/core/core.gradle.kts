plugins {
    `publishing-module`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core","1.5.0")
    implementation("org.jetbrains.kotlinx","kotlinx-coroutines-jdk8","1.5.0")

    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")

    implementation(project(":common"))

    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.8")

    compileOnly("com.google.auto.service", "auto-service-annotations", "1.0-rc7")
    kapt("com.google.auto.service", "auto-service", "1.0-rc7")
}

tasks.compileKotlin.get().kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"