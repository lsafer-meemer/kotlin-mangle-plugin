# Kotlin Mangle Plugin

A Kotlin compiler plugin that enables function name mangling using the `@Mangle` annotation.

## Overview

The Mangle plugin modifies function names at the FIR (Frontend Intermediate Representation) level before any other processing occurs. Function names are appended with a suffix based on a hash of their signature shape, including generic type parameters.

## Features

- **FIR-level Transformation**: Mangles function names before any other compiler processing
- **Signature-based Hashing**: Hash is derived from function shape including generics
- **Interface Support**: Automatically mangles implementations of interface functions marked with `@Mangle`
- **Seamless Integration**: Works with the Kotlin compiler as a standard compiler plugin

## Usage

```kotlin
import com.lsafer.mangle.Mangle

@Mangle
fun processData(input: String): String {
    return input.uppercase()
}

// Function name becomes something like: processData$a1b2c3
```

## Interface Support

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

## Installation

(To be updated with Maven/Gradle dependency information)

## Building

```bash
gradlew build
```

## License

MIT
