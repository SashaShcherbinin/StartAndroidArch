package feature.user.data.repository.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("results")
    val users: List<UserDto>,
)
