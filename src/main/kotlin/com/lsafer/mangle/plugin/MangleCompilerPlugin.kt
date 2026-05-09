package com.lsafer.mangle.plugin

import org.jetbrains.kotlin.compiler.plugin.CompilerPlugin
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

/**
 * Main compiler plugin entry point for the Mangle plugin.
 *
 * This plugin registers a FIR extension that performs function name mangling
 * at the FIR level before any other compiler processing.
 */
class MangleCompilerPlugin : CompilerPlugin {
    override fun getName(): String = "kotlin-mangle-plugin"
}

/**
 * Registrar for Mangle FIR extensions.
 */
class MangleFirExtensionRegistrar : FirExtensionRegistrar() {
    override fun registerExtensions(session: FirSession, extensions: MutableList<FirExtension>) {
        extensions.add(MangleFirExtension(session))
    }
}

/**
 * Registrar for the Mangle compiler plugin.
 */
class MangleCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        FirExtensionRegistrar.registerExtension(MangleFirExtensionRegistrar())
    }

    companion object {
        // Version for compatibility checking
        const val PLUGIN_VERSION = "0.1.0"
    }
}
