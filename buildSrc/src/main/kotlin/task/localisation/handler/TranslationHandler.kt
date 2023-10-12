package task.localisation.handler

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.withCharset
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import org.gradle.api.Project
import task.localisation.entity.GoogleRequest
import task.localisation.entity.GoogleResponse
import task.localisation.entity.StringRes
import task.localisation.languageKeyLists

private const val API_CODE = "real_api_code" // my

internal class TranslationHandler(project: Project) {

    private val jsonHandler = JsonHandler(project)

    @OptIn(InternalAPI::class)
    suspend fun translate(sourceLanguage: String, text: String): List<StringRes> {
        val client = HttpClient {
            install(plugin = ContentNegotiation) {
                json(json = jsonHandler.json)
            }
        }

        val sourceStringRes = StringRes(
            language = languageKeyLists.find { it.google == sourceLanguage }!!.android,
            text = text,
        )
        val stringResList = languageKeyLists
            .filter { it.google != sourceLanguage }
            .map { languageKey ->
                val needToTranslate = GoogleRequest(
                    text = text,
                    targetLanguage = languageKey.google,
                    sourceLanguage = sourceLanguage,
                    format = "text",
                )

                val response: HttpResponse =
                    client.post(urlString = "https://translation.googleapis.com/language/translate/v2") {
                        contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
                        parameter("key", API_CODE)
                        body = jsonHandler.json.encodeToString(needToTranslate)
                    }

                val googleTranslateResponse = response.body<GoogleResponse>()
                val translation = googleTranslateResponse.data.translations.first().translatedText
                StringRes(
                    language = languageKey.android,
                    text = translation,
                ).apply { println(this) }
            } + sourceStringRes

        client.close()

        return stringResList
    }
}