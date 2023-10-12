@file:Suppress("unused")

package base.domain.extention

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import java.io.Serializable

fun <T1, T2> Flow<T1>.zipWith(other: Flow<T2>): Flow<Pair<T1, T2>> = zip(other, ::Pair)

fun <T1, T2> Flow<T1>.combineWith(other: Flow<T2>): Flow<Pair<T1, T2>> =
    combine(other, ::Pair)

fun <T1, T2> combine(flow1: Flow<T1>, flow2: Flow<T2>): Flow<Pair<T1, T2>> =
    flow1.combineWith(flow2)

fun <T1, T2, T3> combine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
): Flow<Triple<T1, T2, T3>> = combine(flow1, flow2, flow3, ::Triple)

fun <T1, T2, T3, T4> combine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
): Flow<PairPair<T1, T2, T3, T4>> = combine(flow1, flow2, flow3, flow4, ::PairPair)

fun <T1, T2, T3, T4, T5> combine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
): Flow<TriplePair<T1, T2, T3, T4, T5>> = combine(flow1, flow2, flow3, flow4, flow5, ::TriplePair)

data class PairPair<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val quarter: D,
) : Serializable

data class TriplePair<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val quarter: D,
    val fifth: E
) : Serializable
