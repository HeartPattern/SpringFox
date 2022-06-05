plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("org.jetbrains.dokka")

    id("io.spring.dependency-management")

    `maven-publish`
    signing
}

repositories {
    maven("https://repo.heartpattern.io/repository/maven-public")
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka", "kotlin-as-java-plugin", Versions.DOKKA)
}

tasks.compileKotlin.get().kotlinOptions{
    freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    jvmTarget = "1.8"
}

tasks.create<Jar>("sourceJar") {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

tasks.create<Jar>("javadocJar") {
    from(tasks["dokkaJavadoc"].outputs)
    archiveClassifier.set("javadoc")
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
            artifactId = "springfox-" + project.name.replace('.', '-')
            from(components["java"])

            artifact(tasks["sourceJar"]) {
                classifier = "sources"
            }

            artifact(tasks["javadocJar"]) {
                classifier = "javadoc"
            }

            pom {
                name.set("SpringFox")
                description.set("Spring auto configuration module provide paper related feature.")
                url.set("https://github.com/HeartPattern/SpringFox")
                packaging = "jar"

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer{
                        name.set("HeartPattern")
                        email.set("heartpattern@heartpattern.io")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/HeartPattern/SpringFox.git")
                    developerConnection.set("scm:git:git://github.com/HeartPattern/SpringFox.git")
                    url.set("https://github.com/HeartPattern/SpringFox/tree/master")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}