package base.logger

interface AppLog {
    fun d(message: String)

    fun w(message: String)

    fun e(e: Throwable)

    fun withTag(tag: String): AppLog
}

