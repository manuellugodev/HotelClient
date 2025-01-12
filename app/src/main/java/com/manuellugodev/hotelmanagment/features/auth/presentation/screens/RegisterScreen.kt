package com.manuellugodev.hotelmanagment.features.auth.presentation.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.R
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.RegisterViewModel
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterState
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen


@Composable
fun RegisterScreenRoot(navController: NavController, viewModel: RegisterViewModel) {

    RegisterScreen(state = viewModel.state, viewModel::onEvent) {
        navController.navigate(Screen.LoginScreen.route)
    }

}

@Composable
fun RegisterScreen(state:RegisterState,onEvent:(RegisterEvent)->Unit,onNavigateToLogin:()->Unit) {




    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Hotel Managment",
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 15.dp),
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
                value = state.username,
                onValueChange = { onEvent(RegisterEvent.onUsernameEnter(it)) },
                label = { Text(text = "Username") },
                singleLine = true,
                supportingText = if (state.usernameError.isNotEmpty()) {
                    { Text(text = state.usernameError) }
                } else null,
                isError = state.usernameError.isNotEmpty()
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.password,
                label = { Text(text = "Password") },
                onValueChange = { onEvent(RegisterEvent.onPasswordEnter(it)) },
                singleLine = true,
                isError = state.passwordError.isNotEmpty(),
                visualTransformation = if (state.isShowingPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, Icons.Default.Lock.name)
                }, trailingIcon = {
                    IconButton(
                        onClick = { onEvent(RegisterEvent.isPasswordVisible(!state.isShowingPassword)) },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (state.isShowingPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                supportingText = if (state.passwordError.isNotEmpty()) {
                    { Text(text = state.passwordError) }
                } else null
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, Icons.Default.Lock.name)
                },
                value = state.confirmPassword,
                onValueChange = { onEvent(RegisterEvent.onPasswordConfirmationEnter(it)) },
                label = { Text(text = "Confirm Password") },
                singleLine = true,
                isError = state.passwordError.isNotEmpty(),
                visualTransformation = if (state.isShowingPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),

                )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
                value = state.firstName,
                onValueChange = { onEvent(RegisterEvent.onFirstNameEnter(it)) },
                label = { Text(text = "First Name") },
                singleLine = true,

                )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
                value = state.lastName,
                onValueChange = { onEvent(RegisterEvent.onLastNameEnter(it)) },
                label = { Text(text = "Last Name") },
                singleLine = true,

                )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.onEmailEnter(it)) },
                label = { Text(text = "Email") },
                singleLine = true,
                isError = state.emailError.isNotEmpty(),
                supportingText = if (state.emailError.isNotEmpty()) {
                    { Text(text = "Enter a valid Email") }
                } else null
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "") },
                value = state.phone,
                onValueChange = { onEvent(RegisterEvent.onPhoneEnter(it)) },
                label = { Text(text = "Phone") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number // Set the keyboard to numeric mode
                ),

            )


        }


        Button(onClick = { onEvent(RegisterEvent.submitDataUser) }) {
            Text(text = "Submit")
        }

        if (state.msgError.isNotEmpty()) {
            ErrorSnackbar(errorMessage = state.msgError) {
                onEvent(RegisterEvent.dismissError)
            }
        }
        if (state.navigateToLogin) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { onNavigateToLogin() }) {
                        Text(
                            text = "Dismiss",
                            modifier = Modifier.padding(8.dp),
                            if (isSystemInDarkTheme()) Color.Black else Color.White
                        )
                    }


                },
                content = {
                    Text(
                        text = stringResource(id = R.string.success_message_register),
                    )
                },

                )
        }



    }


}