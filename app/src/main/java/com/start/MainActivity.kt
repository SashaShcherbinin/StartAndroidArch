package com.start

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import base.compose.local.LocalNavigation
import base.compose.theme.AppTheme
import feature.dashboard.navigation.SCREEN_DASHBOARD
import feature.dashboard.presentation.DashboardScreen
import feature.splash.presentation.SplashScreen
import feature.splash.presentation.navigation.SCREEN_SPLASH
import feature.user.domain.navigation.SCREEN_USER
import feature.user.presentation.UserScreen
import org.koin.compose.KoinContext

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext {
                AppTheme {
                    MainContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainContent() {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavigation provides navController) {
            NavHost(navController, startDestination = SCREEN_SPLASH) {
                composable(SCREEN_SPLASH) { SplashScreen() }
                composable(SCREEN_DASHBOARD) { DashboardScreen() }
                composable(
                    "$SCREEN_USER/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    UserScreen(
                        userId = backStackEntry.arguments?.getString("userId")!!,
                    )
                }
            }
        }

    }
}
