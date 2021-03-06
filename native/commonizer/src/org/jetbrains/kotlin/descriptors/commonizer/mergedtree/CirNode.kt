/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.mergedtree

import org.jetbrains.kotlin.descriptors.commonizer.cir.*
import org.jetbrains.kotlin.descriptors.commonizer.utils.CommonizedGroup
import org.jetbrains.kotlin.storage.NullableLazyValue

interface CirNode<T : CirDeclaration, R : CirDeclaration> {
    val targetDeclarations: CommonizedGroup<T>
    val commonDeclaration: NullableLazyValue<R>

    fun <T, R> accept(visitor: CirNodeVisitor<T, R>, data: T): R

    companion object {
        inline val CirNode<*, *>.indexOfCommon: Int
            get() = targetDeclarations.size

        internal inline val CirNode<*, *>.dimension: Int
            get() = targetDeclarations.size + 1

        fun toString(node: CirNode<*, *>) = buildString {
            if (node is CirPackageNode) {
                append("packageName=").append(node.packageName).append(", ")
            }
            if (node is CirNodeWithClassifierId) {
                append("classifierId=").append(node.classifierId).append(", ")
            }
            append("target=")
            node.targetDeclarations.joinTo(this)
            append(", common=")
            append(if (node.commonDeclaration.isComputed()) node.commonDeclaration() else "<NOT COMPUTED>")
        }
    }
}

interface CirNodeWithClassifierId<T : CirClassifier, R : CirClassifier> : CirNode<T, R> {
    val classifierId: CirEntityId
}

interface CirNodeWithLiftingUp<T : CirDeclaration, R : CirDeclaration> : CirNode<T, R> {
    val isLiftedUp: Boolean
        get() = (commonDeclaration() as? CirLiftedUpDeclaration)?.isLiftedUp == true
}

interface CirNodeWithMembers<T : CirDeclaration, R : CirDeclaration> : CirNode<T, R> {
    val properties: MutableMap<PropertyApproximationKey, CirPropertyNode>
    val functions: MutableMap<FunctionApproximationKey, CirFunctionNode>
    val classes: MutableMap<CirName, CirClassNode>
}
