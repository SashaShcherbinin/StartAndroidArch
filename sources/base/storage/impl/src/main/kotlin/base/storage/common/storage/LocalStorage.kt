@file:Suppress("MemberVisibilityCanBePrivate", "OPT_IN_USAGE")

package base.storage.common.storage

import base.storage.common.cashe.CachePolicy
import base.storage.common.cashe.CachedEntry
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.Result.Companion.success

/**
 * Automatic caching values from network to memory and database(optional) by key ->value
 * @param maxElements max elements after adding making clean up for the first element
 * @param network network method request to get data from network
 * @param permanentStorage data base interface to get, insert, or clean data from data base
 * */
class LocalStorage<K : Any, E>(
    private val maxElements: Int,
    private val cachePolicy: CachePolicy,
    private val network: (suspend (K) -> E),
    private val permanentStorage: PermanentStorage<K, E>? = null
) {

    private val log = Logger.getLogger(this::class.simpleName)
    private val updateChannel = Channel<Unit>(1)
    private val cache: MutableMap<K, CachedEntry<E>> = mutableMapOf()

    fun get(key: K): Flow<Result<E>> {
        val flow: Flow<Result<E>> = flow {
            val cachedEntry: CachedEntry<E>? = cache[key]
            if (cachedEntry != null) {
                emit(success(cachedEntry.entry))
                if (cachePolicy.isExpired(cachedEntry)) {
                    ignoreNetworkException { fetchData(key) }
                }
            } else {
                val permanentEntry = permanentStorage?.read(key)
                if (permanentEntry != null) {
                    addNewValue(key, permanentEntry)
                    emit(success(permanentEntry))
                    ignoreNetworkException { fetchData(key) }
                } else {
                    runCatching {
                        fetchData(key)
                    }
                }
            }
        }
        return merge(flowOf(Unit), updateChannel.receiveAsFlow())
            .flatMapLatest { flow }
    }

    private fun addNewValue(key: K, permanentEntry: E) {
        if (cache.size > maxElements) {
            val oldValueKey = cache.minBy {
                it.value.getCreateTime()
            }.key
            cache.remove(oldValueKey)
        }
        cache[key] = CachePolicy.createEntry(permanentEntry)
    }

    private suspend fun ignoreNetworkException(function: suspend () -> Unit) {
        try {
            function()
        } catch (e: Throwable) {
            log.log(Level.SEVERE, "ignore network error", e)
        }
    }

    private suspend fun fetchData(key: K) {
        val value = network(key)
        addNewValue(key, value)
        permanentStorage?.insertOrUpdate(key, value)
        updateChannel.trySend(Unit)
    }

    suspend fun update(
        key: K,
        onUpdateCallback: (E) -> E
    ) {
        val oldEntry = cache[key]?.entry
        if (oldEntry != null) {
            updateEntity(key, onUpdateCallback(oldEntry))
        } else {
            permanentStorage?.read(key)?.let { it ->
                updateEntity(key, onUpdateCallback(it))
            }
        }
    }

    private suspend fun updateEntity(key: K, newEntry: E) {
        permanentStorage?.insertOrUpdate(key, newEntry)
        addNewValue(key, newEntry)
        updateChannel.trySend(Unit)
    }

    suspend fun refresh(key: K) {
        fetchData(key)
    }

    suspend fun clean(key: K) {
        cache.remove(key)
        permanentStorage?.remove(key)
        updateChannel.trySend(Unit)
    }

    suspend fun clean() {
        cache.keys.forEach {
            clean(it)
        }
    }

    interface PermanentStorage<K, E> {
        suspend fun read(key: K): E?
        suspend fun remove(key: K)
        suspend fun insertOrUpdate(key: K, entity: E)
    }
}