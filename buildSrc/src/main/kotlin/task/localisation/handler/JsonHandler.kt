package task.localisation.handler

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.Project
import task.localisation.entity.JsonBackup
import java.io.File

internal class JsonHandler(project: Project) {

    private val locoFile: File = File(project.projectDir, "loco_strings.json")

    val json: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    fun getJsonStrings(resourcePrefix: String): List<JsonBackup> =
        locoFile.takeIf { it.exists() }?.let { file ->
            json.decodeFromString<List<JsonBackup>>(file.readText())
                .filter { it.key.startsWithAndroidPrefix(resourcePrefix) }
        } ?: emptyList()

    fun writeInFile(jsonBackup: List<JsonBackup>) {
        val jsonText = json.encodeToString(jsonBackup)
        locoFile.writeText(jsonText)
    }

    private fun String.startsWithAndroidPrefix(resourcePrefix: String): Boolean =
        false.takeIf { resourcePrefix.isEmpty() } ?: startsWith(
            prefix = resourcePrefix + "_",
            ignoreCase = false,
        )
}