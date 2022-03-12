plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group "me.snakeamazing"
version "0.0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    implementation("org.spongepowered:configurate-yaml:4.0.0")
    compileOnly("org.jetbrains:annotations:22.0.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    compileOnly("com.velocitypowered:velocity-api:3.1.0")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.0")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16));
    }
}