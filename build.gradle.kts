plugins {
    kotlin("jvm") version "2.1.0"
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.lsafer"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin compiler
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.0")
    
    // Testing
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.23")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            groupId = "com.lsafer"
            artifactId = "kotlin-mangle-plugin"
            version = "0.1.0"
        }
    }
}
