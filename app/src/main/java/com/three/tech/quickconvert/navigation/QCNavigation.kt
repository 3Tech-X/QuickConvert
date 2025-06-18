package com.three.tech.quickconvert.navigation

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.three.tech.quickconvert.ConverterService

const val HOME_SCREEN = "home_page"

@Composable
fun QCNavigation(service: ConverterService, context: Activity) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        qcNavigation(navController, service, context)
    }
}

fun NavGraphBuilder.qcNavigation(
    navController: NavController,
    service: ConverterService,
    context: Activity
) {
    navigation(startDestination = QCComponentRoute.Home.route, route = HOME_SCREEN) {
        navigationScreens(service, navController, context)
    }
}
