plugins {
    id("java")
}

group = "com.umnirium.mc.frostbite"
version = project.property("version") as String

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.named<ProcessResources>("processResources") {
    filesMatching("paper-plugin.yml") {
        expand("version" to project.property("version"))
    }

    filesMatching("plugin.yml") {
        expand("version" to project.property("version"))
    }
}