plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
}

group = "org.isel.leic15"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.palex65:CanvasLib-jvm:1.0.2") // Adicionar
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17) // or 17 if you prefer
}