package com.three.tech.quickconvert.navigation

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.three.tech.quickconvert.screens.QCHomePage

internal fun NavGraphBuilder.navigationScreens(
    navController: NavController,
    context: Activity,
    ) {
    composable(route = QCComponentRoute.Home.route) {
        QCHomePage {
            context.finish()
        }
    }
}