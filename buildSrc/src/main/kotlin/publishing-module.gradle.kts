plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("org.jetbrains.dokka")

    id("io.spring.dependency-management")

    `maven-publish`
}

repositories {
    maven("https://repo.heartpattern.io/repository/maven-public")
}

dependencies {
    "dokkaHtmlPlugin"("org.jetbrains.dokka", "kotlin-as-java-plugin", "1.4.32")
}

tasks.create<Jar>("sourceJar") {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

tasks.create<Jar>("javadocJar") {
    from(tasks["dokkaJavadoc"].outputs)
    archiveClassifier.set("javadoc")
}

tasks.jar {

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

            artifact(tasks["sourceJar"]) {
                classifier = "sources"
            }

            artifact(tasks["javadocJar"]) {
                classifier = "javadoc"
            }
        }
    }
}