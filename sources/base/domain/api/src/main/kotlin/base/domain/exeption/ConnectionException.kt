package base.domain.exeption

import java.io.IOException

/*
 * Signals that can not reach server because of internet connection
 */
class ConnectionException(systemMessage: String? = null, cause: Throwable? = null) :
    IOException(systemMessage, cause)
