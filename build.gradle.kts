plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "codemc-releases"
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("io.papermc.paper:paper-api:${property("paper_version")}")
    compileOnly("com.github.retrooper:packetevents-spigot:${property("packet_events_version")}")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}