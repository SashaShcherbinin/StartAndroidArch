package task.localisation

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import task.localisation.entity.JsonBackup
import task.localisation.handler.JsonHandler
import task.localisation.handler.TranslationHandler
import task.localisation.handler.XmlHandler

open class LocalisationTask : DefaultTask() {

    @Input
    var resourcePrefix: String = ""

    private val xmlHandler = XmlHandler(project)
    private val jsonHandler = JsonHandler(project)
    private val translateHandler = TranslationHandler(project)

    @TaskAction
    fun runTask() = runBlocking {
        if (resourcePrefix.isEmpty()) return@runBlocking

        removeStringFromXml()
        translateNewStrings()
    }

    private fun removeStringFromXml() {
        val jsonStrings: List<JsonBackup> = jsonHandler.getJsonStrings(resourcePrefix)
        val xmlStrings: Map<String, String> = xmlHandler.getXmlStrings(DEFAULT_LANGUAGE)

        xmlStrings.forEach { (key, value) ->
            if (jsonStrings.none { it.key == key }) xmlHandler.removeKeyWithTranslation(key)
        }
    }

    private suspend fun translateNewStrings() {
        val jsonStrings: List<JsonBackup> = jsonHandler.getJsonStrings(resourcePrefix)
        jsonStrings.forEach { jsonBackup ->
            val androidLanguage = convertGoogleToAndroid(jsonBackup.googleLanguage)
            val xmlStrings: Map<String, String> = xmlHandler.getXmlStrings(androidLanguage)
            val xmlEnValue = xmlStrings[jsonBackup.key]
            if (xmlEnValue != jsonBackup.value || xmlHandler.isKeyTranslated(jsonBackup.key)
                    .not()
            ) {
                val translations = translateHandler
                    .translate(jsonBackup.googleLanguage, jsonBackup.value)
                xmlHandler.removeKeyWithTranslation(jsonBackup.key)
                xmlHandler.addTranslations(jsonBackup.key, translations)
            }
        }
    }
}
