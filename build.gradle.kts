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

    maven {
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    compileOnly("io.papermc.paper:paper-api:${property("paper_version")}")
    compileOnly("com.github.retrooper:packetevents-spigot:${property("packet_events_version")}")

    compileOnly("space.vectrix.ignite:ignite-api:${property("ignite_version")}")
    compileOnly("net.fabricmc:sponge-mixin:0.16.5+mixin.${property("mixins_version")}")
    compileOnly("io.github.llamalad7:mixinextras-common:${property("mixinextras_version")}")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}