plugins {
    kotlin("jvm") version "1.5.10" apply false
    kotlin("plugin.spring") version "1.5.10" apply false
    kotlin("plugin.serialization") version "1.5.10" apply false
    kotlin("kapt") version "1.5.10" apply false
    id("org.jetbrains.dokka") version "1.4.32" apply false

    id("org.springframework.boot") version "2.4.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    `maven-publish`
}

allprojects {
    group = "io.heartpattern.springfox"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    repositories {
        maven("https://repo.heartpattern.io/repository/maven-public")
    }

    if (plugins.hasPlugin("org.jetbrains.kotlin.jvm") && plugins.hasPlugin("maven-publish") && plugins.hasPlugin("org.jetbrains.dokka")) {
        dependencies {
            "dokkaHtmlPlugin"("org.jetbrains.dokka", "kotlin-as-java-plugin", "1.4.32")
        }

        tasks.create<Jar>("sourceJar") {
            from(convention.findByType<JavaPluginConvention>()!!.sourceSets["main"].allSource)
            archiveClassifier.set("sources")
        }

        publishing {
            repositories {
                if ("nexus.username" in properties && "nexus.password" in properties) {
                    maven(
                        if (project.version.toString().endsWith("SNAPSHOT"))
                            "https://repo.heartpattern.io/maven-public-snapshots"
                        else
                            "https://repo.heartpattern.io/maven-public-releases"
                    ) {
                        credentials {
                            username = properties["nexus.username"].toString()
                            password = properties["nexus.password"].toString()
                        }
                    }
                }
            }

            publications {
                create<MavenPublication>("maven") {
                    artifactId = project.name.replace('.', '-')
                    from(components["java"])

                    artifact(tasks["sourceJar"].outputs){
                        classifier = "sources"
                    }

                    artifact(tasks["javadocJar"].outputs){
                        classifier = "javadoc"
                    }
                }
            }
        }
    }
}