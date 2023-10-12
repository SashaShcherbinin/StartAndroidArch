package feature.user.data.repository.user.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("email")
    val email: String,
    @SerialName("login")
    val login: Login,
    @SerialName("name")
    val name: Name,
    @SerialName("picture")
    val picture: Picture,
) {

    @Serializable
    data class Login(
        @SerialName("md5")
        val md5: String,
        @SerialName("password")
        val password: String,
        @SerialName("salt")
        val salt: String,
        @SerialName("sha1")
        val sha1: String,
        @SerialName("sha256")
        val sha256: String,
        @SerialName("username")
        val username: String,
        @SerialName("uuid")
        val uuid: String
    )

    @Serializable
    data class Name(
        @SerialName("first")
        val first: String,
        @SerialName("last")
        val last: String,
        @SerialName("title")
        val title: String
    )

    @Serializable
    data class Picture(
        @SerialName("large")
        val large: String,
        @SerialName("medium")
        val medium: String,
        @SerialName("thumbnail")
        val thumbnail: String
    )

}