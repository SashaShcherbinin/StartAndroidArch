@file:Suppress("UNUSED_PARAMETER", "MaxLineLength", "TooManyFunctions")

package base.domain.extention

import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.new
import kotlin.reflect.KClass

/**
 * Declare a [Module.factory] definition by resolving a constructor reference for the dependency.
 * The resolution is done at compile time by leveraging inline functions, no reflection is required.
 *
 * Example:
 * ```
 * class Model
 *
 * val myModule = module {
 *   factoryOfWithCasting(::ModelImpl, Model::class)
 * }
 * ```
 *
 * @author Sasha Shcherbinin
 *
 * @see new
 */
inline fun <reified R, reified V : Any> Module.factoryCastOf(
    crossinline constructor: () -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1> Module.factoryCastOf(
    crossinline constructor: (T1) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2> Module.factoryCastOf(
    crossinline constructor: (T1, T2) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */

inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }

/**
 * @see factoryCastOf
 */
inline fun <reified R, reified V : Any, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17, reified T18, reified T19, reified T20, reified T21, reified T22> Module.factoryCastOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) -> R,
    cast: KClass<V>,
): KoinDefinition<V> = factory { new(constructor) as V }
