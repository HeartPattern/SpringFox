import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer

plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

allprojects {
    group = "io.heartpattern.springfox"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.heartpattern.io/repository/maven-public")
    }
}

evaluationDependsOnChildren()

val allConfiguration = configurations.create("allShadow")
val partialConfiguration = configurations.create("partialShadow")

dependencies {
    childProjects.forEach { (name, _) ->
        allConfiguration(":$name")
        partialConfiguration(":$name") {
            isTransitive = false
        }
    }
}

fun ShadowJar.handleSpringFiles(){
    isZip64 = true
    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INFO/spring.tooling")
    transform(PropertiesFileTransformer().apply {
        paths = listOf("META-INF/spring.factories")
        mergeStrategy = "append"
    })
}

tasks {
    create<ShadowJar>("buildFatPlugin") {
        handleSpringFiles()
        configurations = listOf(allConfiguration)

    }

    create<ShadowJar>("buildPlugin") {
        handleSpringFiles()
        configurations = listOf(partialConfiguration)
    }
}