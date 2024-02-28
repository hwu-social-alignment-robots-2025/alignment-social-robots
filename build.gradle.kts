plugins {
    kotlin("jvm") version "1.8.0" // Adjust Kotlin version as needed
    application       
}

repositories {
    mavenCentral()
}

dependencies {
    // Add any Kotlin or other dependencies here, e.g.,
    implementation(kotlin("stdlib")) 
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18)) 
    }
}

application {
    mainClass.set("furhat.client.MainKt") 
}
