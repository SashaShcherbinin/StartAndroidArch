package feature.splash.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import base.compose.local.LocalNavigation
import base.compose.navigation.navigateTo
import base.compose.view.LoadingView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    val navController = LocalNavigation.current
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SplashEvent.Navigate -> navController.navigateTo(event.direction)
            }
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LoadingView()
    }
}
