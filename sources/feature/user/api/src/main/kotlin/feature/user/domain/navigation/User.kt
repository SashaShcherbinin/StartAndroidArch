package feature.user.domain.navigation

const val SCREEN_USER = "user"

fun navigateToUser(userId: String) = SCREEN_USER + "/${userId}"