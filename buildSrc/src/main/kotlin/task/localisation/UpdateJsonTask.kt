package task.localisation

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import task.localisation.entity.JsonBackup
import task.localisation.handler.JsonHandler
import task.localisation.handler.TranslationHandler
import task.localisation.handler.XmlHandler

open class UpdateJsonTask : DefaultTask() {

    @Input
    var resourcePrefix: String = ""

    private val xmlHandler = XmlHandler(project)
    private val jsonHandler = JsonHandler(project)

    @TaskAction
    fun runTask() = runBlocking {
        if (resourcePrefix.isEmpty()) return@runBlocking

        val jsonStrings: List<JsonBackup> = jsonHandler.getJsonStrings(resourcePrefix)
        val newJsonString = jsonStrings.toMutableList()
        val xmlStrings = xmlHandler.getXmlStrings(DEFAULT_LANGUAGE)
        xmlStrings.forEach { (key, value) ->
            if (jsonStrings.none { it.key == key }) {
                newJsonString.add(JsonBackup(key, googleLanguage = DEFAULT_LANGUAGE, value))
            }
        }
        jsonHandler.writeInFile(newJsonString.sortedBy { it.key })
    }

}
