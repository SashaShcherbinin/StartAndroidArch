package base.storage.common.cashe

class CachedEntry<E> internal constructor(
    val entry: E,
    private val createTime: Long
) {

    fun getCreateTime(): Long {
        return createTime
    }
}