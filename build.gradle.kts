plugins {
    kotlin("jvm") version "1.8.0" // Adjust Kotlin version as needed
    application       
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://s3-eu-west-1.amazonaws.com/furhat-maven/releases")
    }
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
    }
}

dependencies {
    // Add any Kotlin or other dependencies here, e.g.,
    implementation(kotlin("stdlib"))
    implementation("com.furhatrobotics.furhatos:furhat-commons:2.7.0")
    implementation("com.theokanning.openai-gpt3-java:client:0.12.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18)) 
    }
}

application {
    mainClass.set("furhatos.app.openaichat.MainKt") 
}
