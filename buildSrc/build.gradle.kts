plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://repo.heartpattern.io/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin", "1.8.0")
    implementation("org.jetbrains.kotlin", "kotlin-allopen", "1.8.0")
    implementation("org.jetbrains.kotlin", "kotlin-serialization", "1.8.0")
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin", "1.8.0")
    implementation("org.jetbrains.dokka", "dokka-gradle-plugin", "1.7.20")
    implementation("org.springframework.boot", "spring-boot-gradle-plugin", "3.0.2")
    implementation("io.spring.gradle", "dependency-management-plugin", "1.1.0")
    implementation("io.github.gradle-nexus","publish-plugin","1.1.0")
}
