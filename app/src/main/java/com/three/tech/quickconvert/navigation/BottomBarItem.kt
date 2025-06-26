package com.three.tech.quickconvert.navigation

data class BottomBarItem(
    val label: String,
    val icon: Int,
    val onClick: () -> Unit
)

