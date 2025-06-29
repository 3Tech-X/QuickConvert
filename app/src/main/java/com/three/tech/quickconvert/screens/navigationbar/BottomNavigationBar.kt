package com.three.tech.quickconvert.screens.navigationbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.navbar.FloatingBottomBar
import com.three.tech.quickconvert.navigation.BottomBarItem
import com.three.tech.quickconvert.navigation.NavigationType

@Composable
fun CustomNavigationBar(clickedParam: Int, onNavBarClickedClicked: (NavigationType) -> Unit) {
    val context = LocalContext.current
    FloatingBottomBar(
        items = listOf(
            BottomBarItem(
                label = context.getString(R.string.qc_bottom_convert),
                icon = R.drawable.convert_bottom_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.HOME)
                }
            ),
            BottomBarItem(
                label = context.getString(R.string.qc_bottom_bmi),
                icon = R.drawable.bottom_bmi_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.BMI)
                }
            ),
            BottomBarItem(
                label = context.getString(R.string.qc_bottom_about),
                icon = R.drawable.info_bottom_icon,
                onClick = {
                    onNavBarClickedClicked(NavigationType.ABOUT)
                }
            )
        ),
        clickedIndex = clickedParam,
    )
}
