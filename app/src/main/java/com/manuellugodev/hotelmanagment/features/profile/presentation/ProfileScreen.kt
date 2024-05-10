package com.manuellugodev.hotelmanagment.features.profile.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.features.profile.domain.Profile
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.navigation.navigateAndCleanBackStack


@Composable
fun ProfileScreenRoot(navController: NavController,viewModel: ProfileViewModel){

    val state by viewModel.stateProfile.collectAsState()

    if (state.isLogOut) {
        navController.navigateAndCleanBackStack(Screen.WelcomeScreen.route)
    }
    ProfileScreen(state){
        viewModel.logOutSession()
    }
}
@Composable
fun ProfileScreen(state:ProfileState,logOut:()->Unit) {


    Box(
        Modifier
            .fillMaxSize(1f)
    ) {


        Column(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.showProfile!=null) {
                DataProfile(state.showProfile!!)
            }

            Button(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .wrapContentSize(),
                onClick = { logOut() }) {
                Text(text = "Log out")
            }
        }



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataProfile(data: Profile) {

    Card(
        Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(0.5f), shape = CircleShape
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.Person,
            contentDescription = ""
        )
    }


    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f),
    ) {


        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
            value = data.name,
            onValueChange = {},
            label = { Text(text = "Name") },
            readOnly = true,
            singleLine = true,

            )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
            value = data.email,
            onValueChange = {},
            label = { Text(text = "Email") },
            readOnly = true,
            singleLine = true,

            )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "") },
            value = data.phone,
            onValueChange = {},
            label = { Text(text = "Phone") },
            readOnly = true,
            singleLine = true
        )

    }
}


@Composable
@Preview
fun ProfileScreen() {

    DataProfile(
        data = Profile(
            "manuellugo",
            "Manuel Lugo",
            "manuellugo2000ml@gmail.com",
            "78627815631"
        )
    )
}

@Composable
@Preview
fun test() {

    TextField(
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
        value = "Manuel",
        onValueChange = {},
        label = { Text(text = "Name") },
        enabled = false,
    )
}

