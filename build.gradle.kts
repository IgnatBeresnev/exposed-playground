plugins {
    kotlin("jvm") version "2.1.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val exposedVersion = "0.57.0"
val h2Version = "2.2.224"

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")

    implementation("com.h2database:h2:$h2Version")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
