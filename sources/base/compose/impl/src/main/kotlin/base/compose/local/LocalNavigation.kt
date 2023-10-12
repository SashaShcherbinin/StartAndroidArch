package base.compose.local

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavigation: ProvidableCompositionLocal<NavController> =
    compositionLocalOf { noLocalProvidedFor("NavController") }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
