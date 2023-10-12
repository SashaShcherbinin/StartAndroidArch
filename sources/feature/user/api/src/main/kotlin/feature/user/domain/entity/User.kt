package feature.user.domain.entity

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val photoUrl: String,
)

typealias UserId = String
