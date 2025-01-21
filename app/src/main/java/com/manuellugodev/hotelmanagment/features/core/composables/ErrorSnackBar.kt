package com.manuellugodev.hotelmanagment.features.core.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onDismiss: () -> Unit,
) {
    Snackbar(
        modifier = modifier.padding(16.dp),
        action = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Dismiss",
                    modifier = Modifier.padding(8.dp),
                    if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
            }


        },
        content = {
            Text(
                text = errorMessage,
            )
        },

        )
}