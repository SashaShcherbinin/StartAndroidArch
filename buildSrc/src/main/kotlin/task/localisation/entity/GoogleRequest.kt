package task.localisation.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GoogleRequest(
    @SerialName("q")
    val text: String,
    @SerialName("target")
    val targetLanguage: String,
    @SerialName("source")
    val sourceLanguage: String,
    @SerialName("format")
    val format: String,
)