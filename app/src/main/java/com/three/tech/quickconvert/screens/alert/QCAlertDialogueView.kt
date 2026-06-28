package com.three.tech.quickconvert.screens.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.R

@Composable
fun QCErrorPopUp(onDismiss: () -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = context.getString(R.string.qc_alert_dialog_title),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = context.getString(R.string.qc_alert_dialog_message),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .padding(8.dp),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text(text = context.getString(R.string.qc_alert_dialog_positive))
                }

                Button(
                    onClick = {
                        onClose()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(8.dp),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text(text = context.getString(R.string.qc_alert_dialog_negative))
                }
            }
        }
    )
}
