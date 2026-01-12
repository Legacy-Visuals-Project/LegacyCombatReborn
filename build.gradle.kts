plugins {
	kotlin("jvm") version "2.3.0"
	id("com.gradleup.shadow") version "9.3.1"
	id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

java {
	val javaVersion = JavaVersion.VERSION_21
	sourceCompatibility = javaVersion
	targetCompatibility = javaVersion
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
	jvmToolchain(21)
}

paperweight {
	javaLauncher = javaToolchains.launcherFor {
		languageVersion = JavaLanguageVersion.of(21)
	}
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

	paperweight.paperDevBundle(property("paper_version") as String)
	compileOnly("com.github.retrooper:packetevents-spigot:${property("packet_events_version")}")
}

tasks {
	withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
	}
}
