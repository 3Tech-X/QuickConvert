package com.three.tech.quickconvert.navigation

sealed class QCComponentRoute(val route: String) {
    data object Home : QCComponentRoute("home_screen")
    data object About : QCComponentRoute("about_screen")
    data object BMI : QCComponentRoute("bmi_screen")
}