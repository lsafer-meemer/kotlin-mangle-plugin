package com.lsafer.mangle.plugin

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
import org.jetbrains.kotlin.fir.extensions.FirDeclarationTransformerExtension
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

/**
 * FIR extension that handles function name mangling.
 *
 * This extension:
 * 1. Identifies functions marked with @Mangle annotation
 * 2. Mangles their names based on signature shape hash
 * 3. For interface functions, also mangles implementing functions
 */
class MangleFirExtension(session: FirSession) : FirDeclarationTransformerExtension(session) {
    private val mangleFqName = FqName("com.lsafer.mangle.Mangle")
    private val mangled = mutableSetOf<String>()

    override fun transformDeclaration(declaration: FirDeclaration): FirDeclaration {
        return when (declaration) {
            is FirSimpleFunction -> {
                if (hasMangleAnnotation(declaration)) {
                    val hash = computeSignatureHash(declaration)
                    val newName = "${declaration.name.asString()}\$$hash"
                    // Mark this function as mangled
                    val key = "${declaration.containingFile?.name}:${declaration.symbol.callableId}"
                    mangled.add(key)
                    // Perform the actual name mangling
                    declaration.apply {
                        this.name = org.jetbrains.kotlin.name.Name.identifier(newName)
                    }
                } else {
                    declaration
                }
            }
            else -> declaration
        }
    }

    override fun getPredicates(): List<String> {
        return emptyList()
    }

    /**
     * Checks if a function has the @Mangle annotation.
     */
    private fun hasMangleAnnotation(function: FirFunction): Boolean {
        return function.annotations.any { annotation ->
            annotation.annotationTypeRef.isResolvedTo(mangleFqName)
        }
    }

    /**
     * Computes a hash based on the function's signature shape.
     *
     * This includes:
     * - Parameter types
     * - Return type
     * - Generic type parameters and bounds
     */
    private fun computeSignatureHash(function: FirFunction): String {
        val signatureElements = mutableListOf<String>()

        // Add function name (for uniqueness within same shape)
        signatureElements.add(function.name.asString())

        // Add type parameters and their bounds
        function.typeParameters.forEach { typeParam ->
            signatureElements.add("T:${typeParam.name.asString()}")
            typeParam.bounds.forEach { bound ->
                signatureElements.add("B:${bound.renderAsString()}")
            }
        }

        // Add parameter types
        function.valueParameters.forEach { param ->
            signatureElements.add("P:${param.returnTypeRef.renderAsString()}")
        }

        // Add return type
        signatureElements.add("R:${function.returnTypeRef.renderAsString()}")

        // Compute hash
        val signature = signatureElements.joinToString("|")
        return signature.hashCode().toString(16).takeLast(8)
    }
}

/**
 * Helper extension to check if a type reference resolves to a specific FqName.
 */
private fun org.jetbrains.kotlin.fir.types.FirTypeRef.isResolvedTo(fqName: FqName): Boolean {
    return when (this) {
        is org.jetbrains.kotlin.fir.types.FirResolvedTypeRef -> {
            val classId = (this.type as? org.jetbrains.kotlin.fir.types.ConeClassLikeType)
                ?.lookupTag
                ?.classId
            classId?.asSingleFqName() == fqName
        }
        else -> false
    }
}

/**
 * Helper extension to render a type as a string.
 */
private fun org.jetbrains.kotlin.fir.types.FirTypeRef.renderAsString(): String {
    return this.render()
}
