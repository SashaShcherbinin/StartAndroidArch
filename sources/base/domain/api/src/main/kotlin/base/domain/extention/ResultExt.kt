package base.domain.extention

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> runCatchingWithContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> T,
): Result<T> = runCatchingExceptCancellation { withContext(context = context, block = block) }

inline fun <R> runCatchingExceptCancellation(block: () -> R): Result<R> {
    return runCatching(block).exceptCancellation()
}

inline fun <reified T : Throwable, R> Result<R>.except(): Result<R> {
    return onFailure { if (it is T) throw it }
}

fun <T> Result<T>.exceptCancellation(): Result<T> {
    return except<CancellationException, T>()
}

inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return map(transform).flatten()
}

fun <T> Result<Result<T>>.flatten(): Result<T> {
    return fold(
        onSuccess = { it },
        onFailure = { Result.failure(it) },
    )
}

fun <T1, T2, R> combineResult(r1: Result<T1>, r2: Result<T2>, callback: (T1, T2) -> R): Result<R> =
    r1.flatMap { t1 ->
        r2.flatMap { t2 ->
            runCatching {
                callback(t1, t2)
            }
        }
    }

@Suppress("NOTHING_TO_INLINE")
inline fun <T> T.success(): Result<T> = Result.success(this)

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Throwable.failure(): Result<T> = Result.failure(this)
