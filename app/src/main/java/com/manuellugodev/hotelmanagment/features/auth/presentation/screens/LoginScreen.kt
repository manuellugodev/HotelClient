package com.manuellugodev.hotelmanagment.features.auth.presentation.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.LoginViewModel
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.navigation.navigateAndCleanBackStack


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {


    val state by viewModel.statusLogin.collectAsState()

    LoginContent(state = state, onEvent = viewModel::onEvent, navigateToRegister = {navController.navigate(Screen.RegisterScreen.route)})

    if (state.loginSuccess) {
        navController.navigateAndCleanBackStack(Screen.ReservationScreen.route)
    } else if (state.showError.isNotEmpty()) {
        ErrorSnackbar(errorMessage = state.showError) {
            viewModel.onEvent(LoginEvent.DismissError)
        }
    }


}

@Composable
fun LoginContent(state: LoginStatus, navigateToRegister:()->Unit,onEvent:(LoginEvent)->Unit) {


    val focusManager = LocalFocusManager.current
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Hotel Managment",
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp
        )

        Box(modifier = Modifier.height(100.dp), contentAlignment = Alignment.Center){
            if(state.showLoader){
                CircularProgressIndicator()
            }
        }

        TextField(
            value = state.usernameEnter,
            onValueChange = { onEvent(LoginEvent.OnUsernameEnter(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            placeholder = { Text(text = "Username") }
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.passwordeEnter,
            placeholder = { Text(text = "Password") },
            onValueChange = { onEvent(LoginEvent.OnPasswordEnter(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(
                    onClick = { onEvent(LoginEvent.VisibilityPassword(!state.showPassword)) },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = if (state.showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                onEvent(LoginEvent.DoLoginEvent)
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Log In")
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("Don't have an Account?"),
            style = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        ) {
            navigateToRegister()
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
