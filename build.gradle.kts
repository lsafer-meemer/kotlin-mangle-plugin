plugins {
    kotlin("jvm") version "2.1.0"
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.github.lsafer-meemer"
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
            groupId = "com.github.lsafer-meemer"
            artifactId = "kotlin-mangle-plugin"
            version = "0.1.0"
            
            pom {
                name.set("Kotlin Mangle Plugin")
                description.set("A Kotlin compiler plugin that enables function name mangling using the @Mangle annotation")
                url.set("https://github.com/lsafer-meemer/kotlin-mangle-plugin")
                
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                
                developers {
                    developer {
                        id.set("lsafer-meemer")
                        name.set("LSAFER MEEMER")
                    }
                }
                
                scm {
                    connection.set("scm:git:https://github.com/lsafer-meemer/kotlin-mangle-plugin.git")
                    developerConnection.set("scm:git:https://github.com/lsafer-meemer/kotlin-mangle-plugin.git")
                    url.set("https://github.com/lsafer-meemer/kotlin-mangle-plugin")
                }
            }
        }
    }
}
