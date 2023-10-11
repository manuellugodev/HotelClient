package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.ui.LoginViewModel
import com.manuellugodev.hotelmanagment.ui.theme.HotelManagmentTheme


@Composable
fun LoginScreen(navController: NavController) {

    val viewmodel = remember {
        LoginViewModel()
    }

    if (viewmodel._statusLogin.value is LoginStatus.Success) {
        navController.navigate(Screen.ReservationScreen.route)
    }


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isShowingPassword by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Hotel Managment",
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(100.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            placeholder = { Text(text = "Username") }
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            visualTransformation = if (isShowingPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(
                    onClick = { isShowingPassword = !isShowingPassword },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isShowingPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Perform login logic here
                focusManager.clearFocus()
                viewmodel.tryLogin("email","password")
                // Example: Check username and password, navigate to the next screen, etc.
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Log In")
        }
    }
}
/*

@Preview
@Composable
fun PreviewLoginScreen() {
    HotelManagmentTheme {
        LoginScreen()
    }
}*/
