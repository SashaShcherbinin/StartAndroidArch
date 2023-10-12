package base.domain.exeption

import java.io.IOException

/*
 * Signals that server is broken, for example 500 error code
 */
class ServerException(systemMessage: String) : IOException(systemMessage)
