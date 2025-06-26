package com.three.tech.quickconvert.screens.navigationbar

import androidx.compose.runtime.Composable
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.navbar.FloatingBottomBar
import com.three.tech.quickconvert.navigation.BottomBarItem
import com.three.tech.quickconvert.navigation.NavigationType

@Composable
fun CustomNavigationBar(clickedParam: Int, onNavBarClickedClicked: (NavigationType) -> Unit) {
    FloatingBottomBar(
        items = listOf(
            BottomBarItem(
                label = "Convert",
                icon = R.drawable.convert_bottom_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.HOME)
                }
            ),
            BottomBarItem(
                label = "BMI",
                icon = R.drawable.bottom_bmi_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.BMI)
                }
            ),
            BottomBarItem(
                label = "About",
                icon = R.drawable.info_bottom_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.ABOUT)
                }
            )
        ),
        clickedIndex = clickedParam,
    )
}
