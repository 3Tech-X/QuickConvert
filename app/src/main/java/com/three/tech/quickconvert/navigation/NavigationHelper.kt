package com.three.tech.quickconvert.navigation

import androidx.navigation.NavController

fun bottomBarClickHandler(
    navigationType: NavigationType,
    navController: NavController
) {
    when (navigationType) {
        NavigationType.ABOUT -> navController.navigate(QCComponentRoute.About.route)
        NavigationType.HOME -> navController.navigate(QCComponentRoute.Home.route)
        NavigationType.BMI -> navController.navigate(QCComponentRoute.BMI.route)
    }
}
