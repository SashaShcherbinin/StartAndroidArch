package base.compose.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.navigation.NavController

sealed class Direction

data class Forward(
    val route: String,
    val popUpRoute: String? = null,
    val inclusive: Boolean = true,
) : Direction()

data object Back : Direction()

fun NavController.navigateTo(direction: Direction) {
    when (direction) {
        is Forward -> navigate(direction.route) {
            direction.popUpRoute?.let { popBackStack(it, inclusive = direction.inclusive) }
        }

        Back -> goBack(context)
    }
}

private fun NavController.goBack(context: Context) {
    if (popBackStack().not()) context.findActivity().finish()
}

private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}
