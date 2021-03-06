/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger.evaluate

import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import org.jetbrains.kotlin.util.AbstractKotlinBundle

@NonNls
private const val BUNDLE = "messages.KotlinDebuggerEvaluationBundle"

object KotlinDebuggerEvaluationBundle : AbstractKotlinBundle(BUNDLE) {
    @JvmStatic
    fun message(
        @NonNls @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): String = getMessage(key, *params)
}