package com.three.tech.quickconvert.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.three.tech.quickconvert.R
import com.three.tech.quickconvert.navbar.FloatingBottomBar
import com.three.tech.quickconvert.navigation.BottomBarItem

@Composable
fun CustomNavigationBar(context: Context) {
    FloatingBottomBar(
        items = listOf(
            BottomBarItem(
                label = "Convert",
                icon = R.drawable.convert_bottom_icon,
                onClick = {
                    Toast.makeText(
                        context,
                        "Convert Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ),
            BottomBarItem(
                label = "BMI",
                icon = R.drawable.bottom_bmi_icon,
                onClick = {
                    Toast.makeText(
                        context,
                        "BMI Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ),
            BottomBarItem(
                label = "About",
                icon = R.drawable.info_bottom_icon,
                onClick = {
                    Toast.makeText(
                        context,
                        "About Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        )
    )
}
