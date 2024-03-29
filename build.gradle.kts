plugins {
    id("io.github.gradle-nexus.publish-plugin")
}

allprojects {
    group = "io.heartpattern.springfox"
    version = "0.1.15"
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.heartpattern.io/repository/maven-public")
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https:///s01.oss.sonatype.org/content/repositories/snapshots"))
        }
    }
}
