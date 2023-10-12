package base.data.module

import base.logger.AppLog
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpPlainText
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

private const val REQUEST_TIMEOUT_MILLIS: Long = 15000L
private const val CONNECTION_TIMEOUT_MILLIS: Long = 15000L
private const val SOCKET_TIMEOUT_MILLIS: Long = 15000L

fun networkModule(): Module = module {
    factory<Json> {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
    factory {
        HttpClient(engineFactory = Android) {
            val log = get<AppLog>()

            install(plugin = ContentNegotiation) {
                json(json = get())
            }

            install(plugin = Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        log.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(plugin = HttpPlainText)

            install(plugin = HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECTION_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
            }
        }
    }
}
