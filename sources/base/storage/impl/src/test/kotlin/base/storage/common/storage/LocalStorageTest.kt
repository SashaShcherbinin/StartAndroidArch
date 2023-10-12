package base.storage.common.storage

import base.storage.common.cashe.CachePolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class LocalStorageTest {

    @Test
    fun `should get from network when first time`(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn true
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )

        /* When */
        val list = localStorage.get(key).take(1).toList()

        /* Then */
        Assert.assertEquals("13", list[0])
        verify(networkMock, times(1)).invoke(key)
    }

    @Test
    fun getFromNetworkIfCacheExpired(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn true
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )
        /* When */
        val list1 = localStorage.get(key).take(1).toList()
        whenever(networkMock.invoke(any())).thenReturn("15")
        val list2 = localStorage.get(key).take(2).toList()
        /* Then */
        Assert.assertEquals("13", list1[0])
        Assert.assertEquals("13", list2[0])
        Assert.assertEquals("15", list2[1])
        verify(networkMock, times(2)).invoke(key)
    }

    @Test
    fun getFromCacheIfCacheNotExpired(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )
        /* When */
        val list1 = localStorage.get(key).take(1).toList()
        whenever(networkMock.invoke(any())).thenReturn("15")
        val list2 = localStorage.get(key).take(1).toList()
        /* Then */
        Assert.assertEquals("13", list1[0])
        Assert.assertEquals("13", list2[0])
        verify(networkMock, times(1)).invoke(key)
    }

    @Test
    fun getFromNetworkWithoutDbData(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn null
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(
                        any(),
                        any()
                    )
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        /* When */
        val list1 = localStorage.get(key).take(1).toList()
        /* Then */
        Assert.assertEquals("13", list1[0])
        verify(networkMock, times(1)).invoke(key)
        verify(permanentStorageMock, times(1)).read(key)
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "13")
    }

    @Test
    fun getFromDbAndNetwork(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn "100"
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(
                        any(),
                        any()
                    )
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        /* When */
        val list1 = localStorage.get(key).take(2).toList()
        /* Then */
        Assert.assertEquals("100", list1[0])
        Assert.assertEquals("13", list1[1])
        verify(networkMock, times(1)).invoke(key)
        verify(permanentStorageMock, times(1)).read(key)
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "13")
    }

    @Test
    fun getFromDbAndNetworkWhenCacheExpired(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn true
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn "100"
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(
                        any(),
                        any()
                    )
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        /* When */
        val list1 = localStorage.get(key).take(2).toList()
        whenever(networkMock.invoke(any())).thenReturn("15")
        val list2 = localStorage.get(key).take(3).toList()
        /* Then */
        Assert.assertEquals("100", list1[0])
        Assert.assertEquals("13", list1[1])
        Assert.assertEquals("13", list2[0])
        Assert.assertEquals("15", list2[1])
        Assert.assertEquals("15", list2[2])
        verify(networkMock, times(3)).invoke(key)
        verify(permanentStorageMock, times(1)).read(key)
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "13")
        verify(permanentStorageMock, times(2))
            .insertOrUpdate(key, "15")
    }

    @Test
    fun getFromCacheWhenCacheNotExpired(): Unit = runTest {
        /* Given */
        val key = "1"
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn "100"
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(any(), any())
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        val list1 = localStorage.get(key).take(2).toList()
        Mockito.reset(networkMock)
        Mockito.reset(permanentStorageMock)
        /* When */
        val list2 = localStorage.get(key).take(1).toList()
        /* Then */
        Assert.assertEquals("100", list1[0])
        Assert.assertEquals("13", list1[1])
        Assert.assertEquals("13", list2[0])
        verify(networkMock, times(0)).invoke(key)
        verify(permanentStorageMock, times(0)).read(key)
        verify(permanentStorageMock, times(0))
            .insertOrUpdate(key, "13")
    }

    @Test
    fun updateValue(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )
        /* When */
        val list1 = localStorage.get(key).take(1).toList()
        val list2 = mutableListOf<String>()

        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(3)
                .collect {
                    list2.add(it)
                    if (mutex.isLocked) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            localStorage.update(key) {
                "16"
            }
        }
        mutex.lock()
        mutex.withLock {
            localStorage.update(key) {
                "18"
            }
        }
        differ.await()
        /* Then */
        Assert.assertEquals("13", list1[0])
        Assert.assertEquals("13", list2[0])
        Assert.assertEquals("16", list2[1])
        Assert.assertEquals("18", list2[2])
    }

    @Test
    fun refresh(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )

        /* When */
        val list = mutableListOf<String>()
        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(2)
                .collect {
                    list.add(it)
                    if (mutex.isLocked) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            whenever(networkMock.invoke(any()))
                .thenReturn("15")
            localStorage.refresh(key)
        }
        differ.await()
        /* Then */
        Assert.assertEquals("13", list[0])
        Assert.assertEquals("15", list[1])
        verify(networkMock, times(2)).invoke(key)
    }

    @Test
    fun cleanMemory(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )

        /* When */
        val list = mutableListOf<String>()
        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(2)
                .collect {
                    list.add(it)
                    if (mutex.isLocked) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            whenever(networkMock.invoke(any()))
                .thenReturn("15")
            localStorage.clean()
        }
        differ.await()
        /* Then */
        Assert.assertEquals("13", list[0])
        Assert.assertEquals("15", list[1])
        verify(networkMock, times(2)).invoke(key)
    }

    @Test
    fun cleanMemoryAndDb(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn "100"
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(
                        any(),
                        any()
                    )
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        /* When */
        val list = mutableListOf<String>()
        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(3)
                .collect {
                    list.add(it)
                    if (mutex.isLocked && list.size >= 2) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            whenever(permanentStorageMock.read(any()))
                .thenReturn(null)
            whenever(networkMock.invoke(any()))
                .thenReturn("15")
            localStorage.clean()
        }
        differ.await()
        /* Then */
        Assert.assertEquals("100", list[0])
        Assert.assertEquals("13", list[1])
        Assert.assertEquals("15", list[2])
        verify(networkMock, times(2)).invoke(key)
        verify(permanentStorageMock, times(2)).read(key)
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "13")
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "15")
        verify(permanentStorageMock, times(1)).remove(key)
    }

    @Test
    fun cleanMemoryByKey(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock
            )

        /* When */
        val list = mutableListOf<String>()
        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(2)
                .collect {
                    list.add(it)
                    if (mutex.isLocked) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            whenever(networkMock.invoke(any()))
                .thenReturn("15")
            localStorage.clean(key)
        }
        differ.await()
        /* Then */
        Assert.assertEquals("13", list[0])
        Assert.assertEquals("15", list[1])
        verify(networkMock, times(2)).invoke(key)
    }

    @Test
    fun cleanMemoryAndDbByKey(): Unit = runTest {
        /* Given */
        val key = "1"
        val mutex = Mutex()
        val cacheParams = mock<CachePolicy> {
            on { isExpired(any()) } doReturn false
        }
        val networkMock = mock<(String) -> String> {
            on { invoke(any()) } doReturn "13"
        }
        val permanentStorageMock =
            mock<LocalStorage.PermanentStorage<String, String>> {
                onBlocking { read(key) } doReturn "100"
                onBlocking { remove(key) } doReturn Unit
                onBlocking {
                    insertOrUpdate(
                        any(),
                        any()
                    )
                } doReturn Unit
            }
        val localStorage: LocalStorage<String, String> =
            LocalStorage(
                maxElements = 1,
                cachePolicy = cacheParams,
                network = networkMock,
                permanentStorage = permanentStorageMock
            )
        /* When */
        val list = mutableListOf<String>()
        val differ = CoroutineScope(Dispatchers.Default).async {
            localStorage.get(key)
                .take(3)
                .collect {
                    list.add(it)
                    if (mutex.isLocked && list.size >= 2) mutex.unlock()
                }
        }
        mutex.lock()
        mutex.withLock {
            whenever(permanentStorageMock.read(any()))
                .thenReturn(null)
            whenever(networkMock.invoke(any()))
                .thenReturn("15")
            localStorage.clean(key)
        }
        differ.await()
        /* Then */
        Assert.assertEquals("100", list[0])
        Assert.assertEquals("13", list[1])
        Assert.assertEquals("15", list[2])
        verify(networkMock, times(2)).invoke(key)
        verify(permanentStorageMock, times(2)).read(key)
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "13")
        verify(permanentStorageMock, times(1))
            .insertOrUpdate(key, "15")
        verify(permanentStorageMock, times(1)).remove(key)
    }

}