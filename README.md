# Kotlin Mangle Plugin

A Kotlin compiler plugin that enables function name mangling using the `@Mangle` annotation.

## Overview

The Mangle plugin modifies function names at the FIR (Frontend Intermediate Representation) level before any other processing occurs. Function names are appended with a suffix based on a hash of their signature shape, including generic type parameters.

## Features

- **FIR-level Transformation**: Mangles function names before any other compiler processing
- **Signature-based Hashing**: Hash is derived from function shape including generics
- **Interface Support**: Automatically mangles implementations of interface functions marked with `@Mangle`
- **Seamless Integration**: Works with the Kotlin compiler as a standard compiler plugin

## Installation

### Gradle

Add JitPack to your repositories:

```gradle
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}
```

Add the plugin dependency:

```gradle
dependencies {
    compileOnly("com.github.lsafer-meemer:kotlin-mangle-plugin:0.1.0")
}
```

Add the compiler plugin configuration:

```gradle
plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

kotlinCompilerPluginClasspath("com.github.lsafer-meemer:kotlin-mangle-plugin:0.1.0")
```

Or use the kotlinCompilerPlugins configuration if available in your Kotlin version.

### Maven

Add JitPack to your repositories:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add the dependency:

```xml
<dependency>
    <groupId>com.github.lsafer-meemer</groupId>
    <artifactId>kotlin-mangle-plugin</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

### Basic Function Mangling

Import and apply the `@Mangle` annotation to any function:

```kotlin
import com.lsafer.mangle.Mangle

@Mangle
fun processData(input: String): String {
    return input.uppercase()
}

// Function name becomes something like: processData$a1b2c3
```

### Generic Functions

The hash includes generic type information:

```kotlin
@Mangle
fun <T> transform(item: T): String {
    return item.toString()
}

// Function name becomes: transform$<hash_including_T>
```

### Interface Implementation

When `@Mangle` is applied to an interface function, all implementing classes will have their corresponding functions mangled as well:

```kotlin
interface DataProcessor {
    @Mangle
    fun process(data: String): String
}

class MyProcessor : DataProcessor {
    override fun process(data: String): String {
        return data.uppercase()
    }
}

// Both interface function and implementation are mangled
```

## Building from Source

```bash
./gradlew build
```

## How It Works

1. The plugin registers a FIR (Frontend Intermediate Representation) extension
2. At FIR transformation time, functions marked with `@Mangle` are identified
3. A hash is computed based on the function's signature shape:
   - Function name
   - Type parameters and their bounds
   - Parameter types
   - Return type
4. The hash is appended to the original function name (e.g., `functionName$hash`)
5. This happens **before** any other compiler processing, ensuring all downstream references use the mangled name

## License

MIT

## Repository

https://github.com/lsafer-meemer/kotlin-mangle-plugin
