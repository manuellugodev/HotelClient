package com.manuellugodev.hotelmanagment.features.auth.presentation.screens

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.RegisterViewModel
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterEvent
import com.manuellugodev.hotelmanagment.features.auth.utils.RegisterState
import com.manuellugodev.hotelmanagment.navigation.Screen


@Composable
fun RegisterScreenRoot(navController: NavController,viewModel: RegisterViewModel){

    RegisterScreen(state = viewModel.state,viewModel::onEvent){
        navController.navigate(Screen.LoginScreen.route)
    }

}

@Composable
fun RegisterScreen(state:RegisterState,onEvent:(RegisterEvent)->Unit,onNavigateToLogin:()->Unit) {


    if (state.navigateToLogin) {
        onNavigateToLogin()
    }

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

                )

            TextField(
                value = state.password,
                label = { Text(text = "Password") },
                onValueChange = { onEvent(RegisterEvent.onPasswordEnter(it)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
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
                }
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

                )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "") },
                value = state.phone,
                onValueChange = { onEvent(RegisterEvent.onPhoneEnter(it)) },
                label = { Text(text = "Phone") },
                singleLine = true
            )


        }


        Button(onClick = { onEvent(RegisterEvent.submitDataUser) }) {
            Text(text = "Submit")
        }
    }


}