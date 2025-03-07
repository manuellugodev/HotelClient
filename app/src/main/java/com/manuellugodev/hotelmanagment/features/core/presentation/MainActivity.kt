package com.manuellugodev.hotelmanagment.features.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manuellugodev.hotelmanagment.features.core.navigation.Navigation
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.presentation.ui.theme.HotelManagmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelManagmentTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(
                    topBar = {
                        when (currentRoute) {
                            Screen.ReservationScreen.route -> {
                                TopBarAppHotel("Reservation")
                            }

                            Screen.MyProfileScreen.route -> {
                                TopBarAppHotel(title = "My Profile")
                            }

                            Screen.MyReservationsScreen.route -> {
                                TopBarAppHotel(title = "My Reservations")
                            }
                        }
                    },
                    bottomBar = {
                        if (currentRoute != Screen.WelcomeScreen.route && currentRoute != Screen.LoginScreen.route && currentRoute!= Screen.RegisterScreen.route) {
                            BottomBar(navController)
                        }
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController)
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAppHotel(title: String) {

    val color = MaterialTheme.colorScheme

    val colorS = color.primary.toString();

    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title, textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
@Preview
fun topBarPreview() {
    TopBarAppHotel(title = "Hotel")
}

@Composable
fun BottomBar(navController: NavHostController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Screen.ReservationScreen.route) },
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.MyReservationsScreen.route) },
            icon = { Icon(Icons.Default.Bed, "Reservations") },
            label = { Text("Reservations") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.MyProfileScreen.route) },
            icon = { Icon(Icons.Default.Person, "Profile") },
            label = { Text("Profile") })

    }
}

@Composable
fun Screen(innerPadding: PaddingValues) {
    val state = rememberScrollState()
    Column(
        Modifier
            .padding(innerPadding)
            .scrollable(state, Orientation.Vertical)
    ) {

    }
}














