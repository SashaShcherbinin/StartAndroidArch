package task.localisation.handler

import org.gradle.api.Project
import org.redundent.kotlin.xml.Attribute
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.parse
import org.redundent.kotlin.xml.xml
import task.localisation.DEFAULT_LANGUAGE
import task.localisation.entity.StringRes
import task.localisation.languageKeyLists
import task.localisation.removeHtmlTags
import task.localisation.replaceApostrophe
import java.io.File


private const val RESOURCE_STRING_FILE = "strings.xml"
private const val RESOURCE_FOLDER_STRING = "values"
private const val RESOURCE_FOLDER = "/src/main/res"
private const val RESOURCE_ENCODING = "utf-8"

private const val XML_TAG_STRING = "string"
private const val XML_TAG_RESOURCES = "resources"
private const val XML_ATTRIBUTE_NAME = "name"

internal class XmlHandler(project: Project) {

    private val projectDir: File = project.projectDir

    fun getXmlStrings(androidLanguage: String): Map<String, String> {
        val translationFile = getLocalisationXmlFile(androidLanguage)

        if (translationFile.exists().not()) return emptyMap()

        val node = parse(translationFile)

        val xmlMap = mutableMapOf<String, String>()

        node.children.filterIsInstance<Node>().forEach { item ->
            if (item.nodeName == XML_TAG_STRING) {
                val value = (item.children.first() as TextElement).text
                val key = item.get<String>(XML_ATTRIBUTE_NAME)!!
                xmlMap[key] = value
            }
        }

        return xmlMap
    }

    fun addTranslations(key: String, translations: List<StringRes>) {
        translations.forEach { stringRes ->
            val translationFile = getLocalisationXmlFile(stringRes.language)

            val node = if (translationFile.exists()) {
                parse(translationFile)
            } else {
                xml(root = XML_TAG_RESOURCES)
            }

            node.element(XML_TAG_STRING) {
                attributes(Attribute(name = XML_ATTRIBUTE_NAME, value = key))
                -(stringRes.text.removeHtmlTags().replaceApostrophe())
            }

            updateLocalisationFile(stringRes.language, node)
        }
    }

    fun isKeyTranslated(key: String): Boolean = languageKeyLists.all { languageKey ->
        val localisationXmlFile = getLocalisationXmlFile(languageKey.android)
        if (localisationXmlFile.exists().not()) return@all false
        val xmlNode = parse(localisationXmlFile)
        xmlNode.children.filterIsInstance<Node>().any { tag ->
            tag.takeIf { node -> node.nodeName == XML_TAG_STRING }
                ?.takeIf { node -> node.get<String>(XML_ATTRIBUTE_NAME) == key }
                ?.let { true }
                ?: false
        }
    }

    fun removeKeyWithTranslation(key: String) {
        languageKeyLists.forEach {
            val localeFile = getLocalisationXmlFile(it.android)
            removeString(xmlFile = localeFile, language = it.android, key = key)
        }
    }

    private fun removeString(xmlFile: File, language: String, key: String) {
        if (xmlFile.exists().not()) return

        val node = parse(xmlFile)
        node.children.filterIsInstance<Node>().find { tag ->
            tag.takeIf { node -> node.nodeName == XML_TAG_STRING }
                ?.takeIf { node -> node.get<String>(XML_ATTRIBUTE_NAME) == key }
                ?.let { true }
                ?: false
        }?.let { element ->
            node.removeElement(element)
            updateLocalisationFile(language = language, node)
        }
    }

    private fun updateLocalisationFile(
        language: String,
        node: Node
    ) {
        val translationFile = getLocalisationXmlFile(language)
        translationFile.deleteRecursively()

        node.apply {
            version = XmlVersion.V10
            encoding = RESOURCE_ENCODING
        }

        val nodes = node.children.filterIsInstance<Node>()
        val sortedNodes = nodes.sortedBy {
            it.get<String>(XML_ATTRIBUTE_NAME)
        }

        node.removeElements(nodes)
        node.addElements(sortedNodes)

        translationFile.writeText(
            node.toString(
                printOptions = PrintOptions(
                    singleLineTextElements = true,
                    indent = "    ",
                )
            )
        )
    }

    private fun getLocalisationXmlFile(language: String): File {
        val resFile = File(projectDir, RESOURCE_FOLDER)
        val translationFolder = if (language == DEFAULT_LANGUAGE) {
            File(resFile, RESOURCE_FOLDER_STRING)
        } else {
            File(resFile, "$RESOURCE_FOLDER_STRING-${language}")
        }
        translationFolder.mkdirs()
        return File(translationFolder, RESOURCE_STRING_FILE)
    }
}