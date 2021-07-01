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