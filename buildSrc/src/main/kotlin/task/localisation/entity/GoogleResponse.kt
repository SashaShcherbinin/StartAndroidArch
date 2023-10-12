package task.localisation.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GoogleResponse(
    @SerialName("data")
    val data: Data
)

@Serializable
internal data class Data(
    @SerialName("translations")
    val translations: List<Translation>
)

@Serializable
internal data class Translation(
    @SerialName("translatedText")
    val translatedText: String
)