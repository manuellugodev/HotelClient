package com.manuellugodev.hotelmanagment.features.auth.presentation.screens

import LOGIN_SCREEN
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.LoginViewModel
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.navigation.navigateAndCleanBackStack


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    Log.i(LOGIN_SCREEN, "Recomposition")

    val state by viewModel.statusLogin

    when (state) {
        LoginStatus.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        else -> LoginContent(viewModel = viewModel){
            navController.navigate(Screen.RegisterScreen.route)
        }
    }

    if (state is LoginStatus.Success) {
        navController.navigateAndCleanBackStack(Screen.ReservationScreen.route)
    } else if (state is LoginStatus.Failure) {
        ErrorSnackbar(errorMessage = "Bad Credentials") {
            viewModel.byDefault()
        }
    }


}

@Composable
fun LoginContent(viewModel: LoginViewModel,onNavigateToRegister:()->Unit) {


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
            placeholder = { Text(text = "Password") },
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
                viewModel.tryLogin(username, password)
                // Example: Check username and password, navigate to the next screen, etc.
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Log In")
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(text = AnnotatedString("Don't have an Account?"), style = TextStyle.Default.copy(fontSize = 20.sp)) {
            onNavigateToRegister()
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
