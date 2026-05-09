package com.lsafer.mangle

/**
 * Annotation to mark a function for name mangling.
 *
 * When applied to a function, this annotation causes the compiler plugin to append a suffix
 * to the function's name based on a hash of its signature shape (including generic type parameters).
 * This happens at the FIR level before any other compiler processing.
 *
 * For interface functions, all implementing classes will have their corresponding functions
 * mangled as well.
 *
 * Example:
 * ```
 * @Mangle
 * fun processData(input: String): String = input.uppercase()
 * // Function name becomes: processData$<hash>
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class Mangle
