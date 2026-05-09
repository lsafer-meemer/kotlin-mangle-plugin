package com.lsafer.mangle

import org.junit.Test
import kotlin.test.assertTrue

class ManglePluginTest {
    @Test
    fun testAnnotationExists() {
        // Simple test to verify the annotation can be used
        val annotation = Mangle::class.java.getAnnotation(Target::class.java)
        assertTrue(annotation != null)
    }

    @Test
    fun testAnnotationTarget() {
        val target = Mangle::class.java.getAnnotation(Target::class.java)
        assertTrue(target != null && target.allowedTargets.contains(AnnotationTarget.FUNCTION))
    }

    @Mangle
    fun testFunction(): String {
        return "test"
    }
}
