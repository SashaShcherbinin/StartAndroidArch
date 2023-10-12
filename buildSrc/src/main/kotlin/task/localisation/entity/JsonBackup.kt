package task.localisation.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonBackup(
    @SerialName("key")
    val key: String,
    @SerialName("language")
    val googleLanguage: String,
    @SerialName("value")
    val value: String
)