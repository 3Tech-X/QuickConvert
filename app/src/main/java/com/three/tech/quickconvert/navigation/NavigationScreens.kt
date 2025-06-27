package com.three.tech.quickconvert.navigation

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.three.tech.quickconvert.screens.about.AboutScreen
import com.three.tech.quickconvert.screens.bmi.BMICalculatorScreen
import com.three.tech.quickconvert.screens.homescreen.QCHomePage

internal fun NavGraphBuilder.navigationScreens(
    navController: NavController,
    context: Activity,
) {
    composable(route = QCComponentRoute.Home.route) {
        QCHomePage(onClose = {
            context.finish()
        }) {
            bottomBarClickHandler(it, navController)
        }
    }

    composable(route = QCComponentRoute.About.route) {
        AboutScreen(
            onBackPress = {
                navController.navigate(QCComponentRoute.Home.route) {
                    popUpTo(QCComponentRoute.Home.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            bottomBarClickHandler(it, navController)
        }
    }

    composable(route = QCComponentRoute.BMI.route) {
        BMICalculatorScreen(
            onBackPress = {
                navController.navigate(QCComponentRoute.Home.route) {
                    popUpTo(QCComponentRoute.Home.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            bottomBarClickHandler(it, navController)
        }
    }
}
